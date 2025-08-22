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

    @Inject(
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD")
    )
    private void tiny_terrors$render(LivingEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!(this instanceof EntityRendererWithBabyModel modelGetter)) return;

        this.model = state.baby ? modelGetter.tiny_terrors$getBabyModel() : this.tiny_terrors$normalModel;
    }
}
