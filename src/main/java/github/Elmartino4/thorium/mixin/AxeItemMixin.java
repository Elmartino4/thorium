package github.Elmartino4.thorium.mixin;

import github.Elmartino4.thorium.ThoriumMod;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public class AxeItemMixin extends MiningToolItem {
    protected AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, Tag<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = target.world;

        if(world instanceof net.minecraft.server.world.ServerWorld && EnchantmentHelper.getLevel(Enchantments.CHANNELING, stack) > 1)
            ((ServerWorld) world).setWeather(0, 0, true, true);

        if (world instanceof net.minecraft.server.world.ServerWorld && world.isThundering() && EnchantmentHelper.hasChanneling(stack)) {
            BlockPos pos = target.getBlockPos();
            if (world.isSkyVisible(pos)) {
                LightningEntity lightning = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world);
                lightning.refreshPositionAfterTeleport(Vec3d.ofBottomCenter((Vec3i)pos));
                lightning.setChanneler((attacker instanceof ServerPlayerEntity) ? (ServerPlayerEntity)attacker : null);
                world.spawnEntity(lightning);
                world.playSound(null, pos, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.WEATHER, 5.0F, 1.0F);

                if(attacker instanceof ServerPlayerEntity){
                    if(attacker.isFallFlying() && stack.getItem() == Items.NETHERITE_AXE && attacker.getVelocity().distanceTo(new Vec3d(0,0,0)) > 0.27){
                        System.out.println("effect");
                        if(!attacker.hasStatusEffect(ThoriumMod.THOR))
                            attacker.addStatusEffect(new StatusEffectInstance(ThoriumMod.THOR, 20 * 10, 1));
                    }
                }
            }
        }

        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }
}
