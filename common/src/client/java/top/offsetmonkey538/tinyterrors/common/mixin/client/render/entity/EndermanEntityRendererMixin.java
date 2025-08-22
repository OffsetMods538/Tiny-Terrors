package top.offsetmonkey538.tinyterrors.common.mixin.client.render.entity;

import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.common.client.render.EntityRendererWithBabyModel;
import top.offsetmonkey538.tinyterrors.common.client.render.ModEntityModelLayers;

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
