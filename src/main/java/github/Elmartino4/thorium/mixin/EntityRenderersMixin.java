package github.Elmartino4.thorium.mixin;

import github.Elmartino4.thorium.entity.ThoriumBombEntity;
import github.Elmartino4.thorium.entity.ThoriumBombEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Map;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {
    @Shadow @Final
    static Map<EntityType<?>, EntityRendererFactory<?>> RENDERER_FACTORIES;

    @Inject(method = "<clinit>()V", at = @At("HEAD"))
    private static void injectClinit(){
        //RENDERER_FACTORIES.put(EntityType, (EntityRendererFactory<ThoriumBombEntity>)ThoriumBombEntityRenderer::new);
    }
}
