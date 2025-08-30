package top.offsetmonkey538.tinyterrors.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.tinyterrors.client.util.EntityRenderContext;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Shadow
    protected EntityModel<?> model;

    @WrapOperation(
            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"
            )
    )
    protected void tiny_terrors$changeBabyCreeperModel(EntityModel<?> instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int light, int overlay, int color, Operation<Void> original, @Local(argsOnly = true) LivingEntity entity) {
        EntityRenderContext.contextualize(() -> original.call(instance, matrixStack, vertexConsumer, light, overlay, color), entity.isBaby());
    }
}
