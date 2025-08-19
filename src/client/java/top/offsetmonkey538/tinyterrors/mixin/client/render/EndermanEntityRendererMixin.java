package top.offsetmonkey538.tinyterrors.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.render.entity.state.EndermanEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;
import top.offsetmonkey538.tinyterrors.mixin.client.render.dummy.DummyLivingEntityRendererMixin;

@Mixin(EndermanEntityRenderer.class)
public abstract class EndermanEntityRendererMixin extends DummyLivingEntityRendererMixin {

    @Unique
    private EndermanEntityModel<?> tiny_terrors$babyModel;
    @Unique
    private EndermanEntityModel<?> tiny_terrors$normalModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tiny_terrors$initBabyModel(EntityRendererFactory.Context context, CallbackInfo ci) {
        tiny_terrors$babyModel = new EndermanEntityModel<>(context.getPart(ModEntityModelLayers.ENDERMAN_BABY));
        tiny_terrors$normalModel = (EndermanEntityModel<?>) this.model;
    }

    @Override
    protected void tiny_terrors$render(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        this.model = ((EndermanEntityRenderState) state).baby ? this.tiny_terrors$babyModel : this.tiny_terrors$normalModel;
        super.tiny_terrors$render(state, matrices, vertexConsumers, light, original);
    }
}
