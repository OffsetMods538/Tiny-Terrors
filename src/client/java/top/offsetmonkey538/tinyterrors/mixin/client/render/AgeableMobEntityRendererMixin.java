package top.offsetmonkey538.tinyterrors.mixin.client.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.tinyterrors.client.render.EntityRendererWithBabyModel;

@SuppressWarnings("deprecation") // hopefully fine 😅
@Mixin(AgeableMobEntityRenderer.class)
public abstract class AgeableMobEntityRendererMixin {

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/AgeableMobEntityRenderer;babyModel:Lnet/minecraft/client/render/entity/model/EntityModel;"
            )
    )
    private EntityModel<?> tiny_terrors$useCorrectBabyModel(EntityModel<?> original) {
        return this instanceof EntityRendererWithBabyModel modelGetter ? modelGetter.tiny_terrors$getBabyModel() : original;
    }
}
