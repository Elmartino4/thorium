package github.elmartino4.thorium;

import github.elmartino4.thorium.items.ThoriumItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoriumMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("Thorium");
	public static final StatusEffect THOR = new ThorStatusEffect();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ThoriumItems Items = new ThoriumItems();
		ThoriumRecipes Recipes = new ThoriumRecipes();
		Registry.register(Registry.STATUS_EFFECT, new Identifier("thorium", "thor"), THOR);
		LOGGER.info("Loaded The Thorium Mod");
	}
}
