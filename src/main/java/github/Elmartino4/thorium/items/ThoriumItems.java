package github.Elmartino4.thorium.items;

import github.Elmartino4.thorium.entity.ThoriumBombEntity;
import github.Elmartino4.thorium.entity.ThoriumBombEntityRenderer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.impl.object.builder.FabricEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ThoriumItems {
    public static Item THORIUM_INGOT;
    public static Item TIERED_NETHERITE_INGOT;
    public static Block THORIUM_BOMB;
    private static Object ThoriumBombEntity;

    public static void init(){
        THORIUM_INGOT = new ThoriumIngot(new FabricItemSettings().group(ItemGroup.MISC).fireproof().rarity(Rarity.UNCOMMON).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier("thorium", "thorium_ingot"), THORIUM_INGOT);

        TIERED_NETHERITE_INGOT = new TieredNetheriteIngot(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier("thorium", "tiered_netherite_ingot"), TIERED_NETHERITE_INGOT);

        THORIUM_BOMB = new ThoriumBomb(FabricBlockSettings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS));
        Registry.register(Registry.BLOCK, new Identifier("thorium", "thorium_bomb"), THORIUM_BOMB);
        Registry.register(Registry.ITEM, new Identifier("thorium", "thorium_bomb"),
                new BlockItem(THORIUM_BOMB, new FabricItemSettings().group(ItemGroup.REDSTONE)));

        //public FabricEntityType(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, boolean bl, boolean summonable,
        //      boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet<Block> spawnBlocks, EntityDimensions entityDimensions,
        //      int maxTrackDistance, int trackTickInterval, Boolean alwaysUpdateVelocity)

        //EntityType ThoriumBombEntityType = new FabricEntityTypeBuilder<ThoriumBombEntity>(SpawnGroup.MISC,
        //        (new EntityType.EntityFactory()).create((TntEntity)ThoriumBombEntity));

        FabricModelPredicateProviderRegistry.register(TIERED_NETHERITE_INGOT,
                new Identifier("thorium", "tiered_netherite_tier"),
                (stack, world, entity, seed) -> stack.getOrCreateNbt().getByte("tier"));
    }
}
