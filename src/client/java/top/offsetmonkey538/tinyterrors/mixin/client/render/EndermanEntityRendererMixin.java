package top.offsetmonkey538.tinyterrors.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EndermanEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.EntityRendererWithBabyModel;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;

@Mixin(EndermanEntityRenderer.class)
public abstract class EndermanEntityRendererMixin implements EntityRendererWithBabyModel {

    @Unique
    private EndermanEntityModel<?> tiny_terrors$babyModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tiny_terrors$initBabyModel(EntityRendererFactory.Context context, CallbackInfo ci) {
        tiny_terrors$babyModel = new EndermanEntityModel<>(context.getPart(ModEntityModelLayers.ENDERMAN_BABY));
    }

    @Unique
    @Override
    public EntityModel<?> tiny_terrors$getBabyModel() {
        return tiny_terrors$babyModel;
    }
}
