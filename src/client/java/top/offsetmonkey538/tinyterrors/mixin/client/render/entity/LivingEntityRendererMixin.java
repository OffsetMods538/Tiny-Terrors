package top.offsetmonkey538.tinyterrors.mixin.client.render.entity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.EntityRendererWithBabyModel;

// This is used for endermen and creepers
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

    @Shadow
    protected EntityModel<?> model;

    @Unique
    private EntityModel<?> tiny_terrors$normalModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tiny_terrors$storeNormalModel(EntityRendererFactory.Context ctx, EntityModel<?> model, float shadowRadius, CallbackInfo ci) {
        this.tiny_terrors$normalModel = model;
    }

    @WrapMethod(
            method = "render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
    )
    private void tiny_terrors$render(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        if (this instanceof EntityRendererWithBabyModel modelGetter) {
            this.model = ((LivingEntityRenderState) state).baby ? modelGetter.tiny_terrors$getBabyModel() : this.tiny_terrors$normalModel;
        }

        original.call(state, matrices, vertexConsumers, light);
    }
}
