package github.elmartino4.thorium;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ThorStatusEffect extends StatusEffect {
    public static final int timeConst = 30 * 20;
    public ThorStatusEffect() {
        super(
                StatusEffectType.BENEFICIAL, // whether beneficial or harmful for entities
                0xC0E34D); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return timeConst * amplifier == duration;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier){
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, amplifier * timeConst, 20));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, amplifier * timeConst, 5));
        super.onApplied(entity, attributes, amplifier);
        for(ItemStack armour : entity.getArmorItems()){
            if(EnchantmentHelper.getLevel(Enchantments.BINDING_CURSE, armour) == 0)
                armour.addEnchantment(Enchantments.BINDING_CURSE, 1);
        }
    }

    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, amplifier * timeConst * 6, 3));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, amplifier * timeConst * 6, 8));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, amplifier * timeConst * 4, 3));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, amplifier * timeConst * 3, 8));
        super.onRemoved(entity, attributes, amplifier);
        for(ItemStack armour : entity.getArmorItems()){
            if(EnchantmentHelper.getLevel(Enchantments.VANISHING_CURSE, armour) == 0)
                armour.addEnchantment(Enchantments.VANISHING_CURSE, 1);
        }
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).addExperience(1 << amplifier); // Higher amplifier gives you EXP faster
        }
        entity.heal(1 << amplifier);
    }
}
