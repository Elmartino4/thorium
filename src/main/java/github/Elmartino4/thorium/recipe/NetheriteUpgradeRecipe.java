package github.elmartino4.thorium.recipe;

import github.elmartino4.thorium.items.ThoriumItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.Stream;

public class NetheriteUpgradeRecipe extends SmithingRecipe {
    public NetheriteUpgradeRecipe(Identifier id) {
        super(id, Ingredient.ofItems(Items.STONE_SWORD), Ingredient.ofItems(ThoriumItems.TIERED_NETHERITE_INGOT), new ItemStack(Items.STONE_SWORD));
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        ItemStack stack0 = inventory.getStack(0);
        ItemStack stack1 = inventory.getStack(1);
        return new ItemStack(stack0.getItem()).isEnchantable() && stack1.getItem() == ThoriumItems.TIERED_NETHERITE_INGOT;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        ItemStack out = inventory.getStack(0).copy();

        ItemStack netherite = inventory.getStack(1);

        Random random = new Random(out.hashCode() + netherite.hashCode());

        if(netherite.getOrCreateNbt().contains("quality")){
            double quality = netherite.getOrCreateNbt().getDouble("quality");
            EnchantmentHelper.enchant(random, out, (int)((random.nextDouble() + 2) * quality), false);
        }
        return out;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
