package github.elmartino4.thorium;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;

public class ThorStatusEffect extends StatusEffect {
    public ThorStatusEffect() {
        super(
                StatusEffectType.BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier){
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, amplifier * 200, 20));
        super.onApplied(entity, attributes, amplifier);
    }

    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, amplifier * 200 * 6, 3));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, amplifier * 200 * 6, 8));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, amplifier * 200 * 4, 3));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, amplifier * 200 * 3, 8));
        super.onRemoved(entity, attributes, amplifier);
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).addExperience(1 << amplifier); // Higher amplifier gives you EXP faster
        }
        entity.heal(1 << amplifier);
    }
}
