package top.offsetmonkey538.tinyterrors.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;
import top.offsetmonkey538.tinyterrors.mixin.client.render.dummy.DummyLivingEntityRendererMixin;

@Mixin(CreeperEntityRenderer.class)
public abstract class CreeperEntityRendererMixin extends DummyLivingEntityRendererMixin {

    @Unique
    private CreeperEntityModel tinyterrors$babyModel;
    @Unique
    private CreeperEntityModel tinyterrors$normalModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tinyterrors$initBabyModel(EntityRendererFactory.Context context, CallbackInfo ci) {
        tinyterrors$babyModel = new CreeperEntityModel(context.getPart(ModEntityModelLayers.CREEPER_BABY));
        tinyterrors$normalModel = (CreeperEntityModel) this.model;
    }

    @Override
    protected void tinyterrors$render(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        this.model = ((CreeperEntityRenderState) state).baby ? tinyterrors$babyModel : this.tinyterrors$normalModel;
        super.tinyterrors$render(state, matrices, vertexConsumers, light, original);
    }

    /*
    @Override
    protected void tinyterrors$render(CreeperEntityRenderState renderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, Operation<Void> original) {
        this.model = tinyterrors$babyModel;
        System.out.println("HI");

        super.tinyterrors$render(renderState, matrixStack, vertexConsumerProvider, light, original);
    }
    */
}
