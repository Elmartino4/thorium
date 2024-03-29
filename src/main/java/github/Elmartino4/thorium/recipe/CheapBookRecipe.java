package github.elmartino4.thorium.recipe;

import github.elmartino4.thorium.ThoriumRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CheapBookRecipe extends SpecialCraftingRecipe{
    public CheapBookRecipe(Identifier arg){
        super(arg);
    }

    public boolean matches(CraftingInventory inv, World world){
        //System.out.println("performed check");

        boolean hasDiamond = false;
        int enchantedBook = 0;

        for (int i = 0; i < inv.size(); i++) {
            ItemStack itmStack = inv.getStack(i);
            if(itmStack.getItem() == Items.DIAMOND){
                hasDiamond = true;
            }else if(itmStack.getItem() == Items.DIAMOND_BLOCK){
                hasDiamond = true;
            }else if(itmStack.getItem() == Items.ENCHANTED_BOOK){
                enchantedBook++;
            }else if(itmStack.getItem() == Items.EXPERIENCE_BOTTLE){ }else if(itmStack.getItem() != Items.AIR){
                return false;
            }
        }

        return hasDiamond && enchantedBook == 1;
    }

    public ItemStack craft(CraftingInventory inv){
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        double diamonds = 0;

        for (int i = 0; i < inv.size(); i++) {
            ItemStack itmStack = inv.getStack(i);
            if(itmStack.getItem() == Items.DIAMOND){
                diamonds++;
            }else if(itmStack.getItem() == Items.DIAMOND_BLOCK){
                diamonds += 11;
            }else if(itmStack.getItem() == Items.EXPERIENCE_BOTTLE){
                diamonds += 0.4D;
            }else if(itmStack.getItem() == Items.ENCHANTED_BOOK){
                book = itmStack.copy();
            }
        }

        if(book.getNbt().contains("high-level-enchants-cost")){
            diamonds += book.getOrCreateNbt().getDouble("high-level-enchants-cost");
        }else{
            diamonds++;
        }

        book.setSubNbt("high-level-enchants-cost", NbtDouble.of(diamonds));

        System.out.println("diamonds = " + diamonds);

        return book;
    }

    public boolean fits(int width, int height) {
        return (width * height >= 2);
    }

    public RecipeSerializer<?> getSerializer() {
        return ThoriumRecipes.CHEAP_BOOKS;
    }
}
