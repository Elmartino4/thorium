package github.elmartino4.thorium.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobEntity.class)
public interface MobEntityAccesor {
    @Accessor("handItems")
    DefaultedList<ItemStack> getHandItems();

    @Accessor("armorItems")
    DefaultedList<ItemStack> getArmorItems();
}
