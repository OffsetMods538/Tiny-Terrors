package top.offsetmonkey538.tinyterrors.mixin.client.render.entity.feature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.SkeletonOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.render.entity.state.SkeletonEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.tinyterrors.client.render.SkeletonEntityRenderer;

// TODO: separate into different subprojects for multiversion
@Mixin(SkeletonOverlayFeatureRenderer.class)
public abstract class SkeletonOverlayFeatureRendererMixin {

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/SkeletonEntityRenderState;FF)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/feature/SkeletonOverlayFeatureRenderer;model:Lnet/minecraft/client/render/entity/model/SkeletonEntityModel;"
            )
    )
    private SkeletonEntityModel<?> tiny_terrors$useBabyOverlayModel(SkeletonEntityModel<?> original, @Local(argsOnly = true) SkeletonEntityRenderState state) {
        if (!state.baby || !(((FeatureRendererAccess) this).getContext() instanceof SkeletonEntityRenderer skeletonRenderer)) return original;

        return skeletonRenderer.tiny_terrors$getOverlayModel() == null ? original : skeletonRenderer.tiny_terrors$getOverlayModel();
    }
}
