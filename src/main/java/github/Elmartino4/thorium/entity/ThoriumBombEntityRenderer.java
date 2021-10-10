package github.elmartino4.thorium.entity;

import github.elmartino4.thorium.items.ThoriumItems;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class ThoriumBombEntityRenderer extends EntityRenderer<ThoriumBombEntity> {
    public ThoriumBombEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(ThoriumBombEntity entity, Frustum frustum, double x, double y, double z) {
        return super.shouldRender(entity, frustum, x, y, z);
    }

    public void render(ThoriumBombEntity tntEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0D, 0.5D, 0.0D);
        int j = tntEntity.getFuse();
        if ((float)j - g + 1.0F < 10.0F) {
            float h = 1.0F - ((float)j - g + 1.0F) / 10.0F;
            h = MathHelper.clamp(h, 0.0F, 1.0F);
            h *= h;
            h *= h;
            float k = 1.0F + h * 0.3F;
            matrixStack.scale(k, k, k);
        }

        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
        matrixStack.translate(-0.5D, -0.5D, 0.5D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
        TntMinecartEntityRenderer.renderFlashingBlock(ThoriumItems.THORIUM_BOMB.getDefaultState(), matrixStack, vertexConsumerProvider, i, j / 5 % 2 == 0);
        matrixStack.pop();
        super.render(tntEntity, f, g, matrixStack, vertexConsumerProvider, i);
        //System.out.println("called renderer");
    }

    public Identifier getTexture(ThoriumBombEntity tntEntity) {
        return new Identifier("thorium", "textures/block/thorium_bomb");
    }
}
