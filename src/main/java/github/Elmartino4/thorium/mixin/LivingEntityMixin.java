package github.elmartino4.thorium.mixin;

import github.elmartino4.thorium.ThoriumMod;
import github.elmartino4.thorium.items.ThoriumItems;
import github.elmartino4.thorium.items.TieredNetheriteIngot;
import github.elmartino4.thorium.items.ThoriumIngot;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract void dropXp();

    @Shadow public abstract float getHealth();

    @Shadow public abstract float getMaxHealth();

    @Shadow @Nullable private LivingEntity attacker;

    @Shadow private LivingEntity attacking;

    @Inject(method = "onStatusEffectRemoved", at = @At("HEAD"), cancellable = true)
    private void cancelMilk(StatusEffectInstance effect, CallbackInfo ci) {
        if (effect.getEffectType() == StatusEffects.WITHER) ci.cancel();
    }

    @Inject(method = "drop", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V"), cancellable = true)
    private void checkDrop(DamageSource source, CallbackInfo ci) {
        Entity attacker = (source.getSource() != null) ? source.getSource() : source.getAttacker();

        System.out.println(attacker.toString());

        if(attacker instanceof LightningEntity){
            System.out.println("by lightning");
            if(((LightningEntity)attacker).getChanneler() != null){
                attacker = ((LightningEntity)attacker).getChanneler();
                System.out.println("used channeler");
            }
        }

        if (attacker instanceof LivingEntity livingAttacker) {

            System.out.println("checking attacker");

            if (livingAttacker.hasStatusEffect(ThoriumMod.THOR)) {
                System.out.println("attacker is thor");
                dropXp();
                double quality = getQuality();

                ItemStack out = new ItemStack(ThoriumItems.THORIUM_INGOT);
                out.getOrCreateNbt().putDouble("quality", quality);
                dropStack(out);

                ci.cancel();
            }
        }
    }

    private double getQuality() {
        double out = 0;

        out += getMaxHealth() * 0.7D;

        if ((Object) this instanceof MobEntity) {
            MobEntityAccesor ent = (MobEntityAccesor) (MobEntity) (Object) this;

            for (ItemStack itm : ent.getArmorItems()) {
                out += getItemQuality(itm);
            }

            for (ItemStack itm : ent.getHandItems()) {
                out += getItemQuality(itm);
            }
        }

        if((Object) this instanceof PlayerEntity){
            PlayerInventory inventory = ((PlayerEntity) (Object) this).getInventory();

            for (ItemStack itm : inventory.main){
                out += getItemQuality(itm);
            }

            for (ItemStack itm : inventory.armor){
                out += getItemQuality(itm);
            }
        }

        return out;
    }

    private static double getItemQuality(ItemStack itm){
        double out = 0;

        if(!itm.isEmpty()) out = 0.01;

        Block block = Block.getBlockFromItem(itm.getItem());
        if(block != Blocks.AIR){
            Material mat = block.getDefaultState().getMaterial();
            if(mat == Material.LEAVES || mat == Material.WOOD || mat == Material.BAMBOO || mat == Material.PLANT){
                out = 0.014;
            }else if(mat == Material.AGGREGATE || mat == Material.STONE){
                out = 0.002;
            }
        }

        if(itm.getItem() == Items.SHULKER_BOX){
            out += 12;

            DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);;
            Inventories.readNbt(itm.getOrCreateNbt(), inventory);
            for (ItemStack otherItm : inventory) {
                out += getItemQuality(itm);
            }
        }

        if(itm.getItem().isEnchantable(itm)){
            Map<Enchantment, Integer> enchantMap = EnchantmentHelper.get(itm);

            for (Enchantment ench : enchantMap.keySet()) {
                out += enchantMap.get(ench) * 0.6D * ((ench.isTreasure()) ? 2.5D : 1) * (ench.getRarity().getWeight() + 1);
            }
        }

        if(itm.getItem() instanceof ArmorItem){
            ArmorMaterial armorMaterial = ((ArmorItem)itm.getItem()).getMaterial();
            EquipmentSlot equipmentSlot = ((ArmorItem)itm.getItem()).getSlotType();

            out += armorMaterial.getDurability(equipmentSlot) / 24D;
        }else if(itm.getItem() instanceof ToolItem){
            out += ((ToolItem) itm.getItem()).getMaterial().getDurability() / 20D;
        }

        if(itm.isDamageable()){
            out += (itm.getMaxDamage() - itm.getDamage()) / 16D;
        }

        if(itm.getItem() instanceof TieredNetheriteIngot || itm.getItem() instanceof ThoriumIngot){
            out += itm.getOrCreateNbt().getDouble("quality") * 0.9D;
        }

        return out * itm.getCount();
    }
}
