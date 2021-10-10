package github.elmartino4.thorium.recipe;

import github.elmartino4.thorium.ThoriumMod;
import github.elmartino4.thorium.items.ThoriumItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TieredNetheriteIngotRecipe extends SpecialCraftingRecipe {
    public TieredNetheriteIngotRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int thoriumCount = 0;
        int netheriteScrapCount = 0;

        //System.out.println("hmm");
        //ThoriumMod.LOGGER.debug("testing recipe");

        for (int i = 0; i < inventory.size(); i++) {
            Item itm = inventory.getStack(i).getItem();
            if(itm == Items.NETHERITE_SCRAP){
                netheriteScrapCount++;
            }else if(itm == ThoriumItems.THORIUM_INGOT){
                thoriumCount++;
            }else if(itm != Items.AIR){
                return false;
            }
        }

        //ThoriumMod.LOGGER.debug("found " + thoriumCount + ", " + netheriteScrapCount);

        return thoriumCount == 4 && netheriteScrapCount == 4;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        double quality = 1;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack itmStack = inventory.getStack(i);
            if(itmStack.getItem() == ThoriumItems.THORIUM_INGOT){
                if(itmStack.getOrCreateNbt().contains("quality"))
                    quality += itmStack.getOrCreateNbt().getDouble("quality");
            }
        }
        ItemStack out = new ItemStack(ThoriumItems.TIERED_NETHERITE_INGOT, 1);
        out.getOrCreateNbt().putDouble("quality", quality);
        return out;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 8;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }
}
