package top.offsetmonkey538.tinyterrors.mixin.client.render.entity.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.tinyterrors.util.CreeperSwirlRenderContext;

@Mixin(EnergySwirlOverlayFeatureRenderer.class)
public abstract class EnergySwirlOverlayFeatureRendererMixin {

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/feature/EnergySwirlOverlayFeatureRenderer;getEnergySwirlModel()Lnet/minecraft/client/render/entity/model/EntityModel;"
            )
    )
    private EntityModel<?> tiny_terrors$useBabyModel(EnergySwirlOverlayFeatureRenderer<?, ?> instance, Operation<EntityModel<?>> original, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderState state, float limbAngle, float limbDistance) {
        return CreeperSwirlRenderContext.contextualize(() -> original.call(instance), state instanceof LivingEntityRenderState livingState && livingState.baby);
    }
}
