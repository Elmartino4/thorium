package github.Elmartino4.thorium.mixin;

import github.Elmartino4.thorium.items.ThoriumItems;
import github.Elmartino4.thorium.ThoriumMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow protected abstract void dropXp();

    @Inject(method = "onStatusEffectRemoved", at = @At("HEAD"), cancellable = true)
    private void cancelMilk(StatusEffectInstance effect, CallbackInfo ci){
        if(effect.getEffectType() == StatusEffects.WITHER) ci.cancel();
    }

    @Inject(method = "drop", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V"), cancellable = true)
    private void checkDrop(DamageSource source, CallbackInfo ci){

        if(source.getAttacker() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity)source.getAttacker();
            if(attacker.hasStatusEffect(ThoriumMod.THOR)){
                dropXp();
                double quality = 0;

                ItemStack out = new ItemStack(ThoriumItems.THORIUM_INGOT);
                out.getOrCreateNbt().putDouble("thorium:quality", quality);
                dropStack(out);
            }
        }
    }
}
