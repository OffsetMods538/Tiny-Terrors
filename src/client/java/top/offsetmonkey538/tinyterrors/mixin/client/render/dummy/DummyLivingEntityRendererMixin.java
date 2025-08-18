package top.offsetmonkey538.tinyterrors.mixin.client.render.dummy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntityRenderer.class)
public abstract class DummyLivingEntityRendererMixin {

    @Shadow
    protected EntityModel<?> model;

    @WrapMethod(
            method = "render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
    )
    protected void tiny_terrors$render(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        original.call(state, matrices, vertexConsumers, light);
    }
}
