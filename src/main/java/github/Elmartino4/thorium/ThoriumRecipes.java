package github.elmartino4.thorium;

import github.elmartino4.thorium.recipe.NetheriteUpgradeRecipe;
import github.elmartino4.thorium.recipe.TieredNetheriteIngotRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ThoriumRecipes {
    public static final SpecialRecipeSerializer<TieredNetheriteIngotRecipe> TIERED_NETHERITE_RECIPE = Registry.register(
            Registry.RECIPE_SERIALIZER,
            new Identifier("thorium","crafting_tiered_netherite"),
            new SpecialRecipeSerializer<>(TieredNetheriteIngotRecipe::new));

    public static final SpecialRecipeSerializer<NetheriteUpgradeRecipe> NETHERITE_UPGRADE_RECIPE = Registry.register(
            Registry.RECIPE_SERIALIZER,
            new Identifier("thorium","netherite_upgrade"),
            new SpecialRecipeSerializer<>(NetheriteUpgradeRecipe::new));
}
