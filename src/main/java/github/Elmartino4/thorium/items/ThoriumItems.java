package github.elmartino4.thorium.items;

import github.elmartino4.thorium.entity.ThoriumBombEntityRenderer;
import github.elmartino4.thorium.entity.ThoriumBombEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ThoriumItems {
    public static final Item THORIUM_INGOT;
    public static final Item TIERED_NETHERITE_INGOT;
    public static final Block THORIUM_BOMB;
    public static final EntityType<ThoriumBombEntity> THORIUM_BOMB_ENTITY_TYPE;

    static {
        THORIUM_INGOT = new ThoriumIngot(new FabricItemSettings().group(ItemGroup.MISC).fireproof().rarity(Rarity.UNCOMMON).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier("thorium", "thorium_ingot"), THORIUM_INGOT);

        TIERED_NETHERITE_INGOT = new TieredNetheriteIngot(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier("thorium", "tiered_netherite_ingot"), TIERED_NETHERITE_INGOT);

        THORIUM_BOMB = new ThoriumBomb(FabricBlockSettings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS));
        Registry.register(Registry.BLOCK, new Identifier("thorium", "thorium_bomb"), THORIUM_BOMB);
        Registry.register(Registry.ITEM, new Identifier("thorium", "thorium_bomb"),
                new BlockItem(THORIUM_BOMB, new FabricItemSettings().group(ItemGroup.REDSTONE)));

        /*EntityType.Builder<ThoriumBombEntity> type = (FabricEntityTypeBuilder.create(SpawnGroup.MISC, ThoriumBombEntity::new)
                .dimensions(new EntityDimensions(0.98F, 0.7F, true))
                .trackRangeBlocks(8)
                .fireImmune()
                .build());*/

        //((EntityType.Builder<? extends Entity>)EntityType.Builder.create(TntEntity::new, SpawnGroup.MISC)).build("thorium_bomb")


        THORIUM_BOMB_ENTITY_TYPE =
                (EntityType)Registry.register(Registry.ENTITY_TYPE,
                        new Identifier("thorium", "thorium_bomb"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MISC, ThoriumBombEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

        EntityRendererRegistry.register(THORIUM_BOMB_ENTITY_TYPE, (context) -> {
            return new ThoriumBombEntityRenderer(context);
        });

        FabricModelPredicateProviderRegistry.register(TIERED_NETHERITE_INGOT,
                new Identifier("thorium", "tiered_netherite_tier"),
                (stack, world, entity, seed) -> stack.getOrCreateNbt().getByte("tier"));
    }
}
