package top.offsetmonkey538.tinyterrors.mixin.client.render.entity;

import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.EntityRendererWithBabyModel;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;

@Mixin(CreeperEntityRenderer.class)
public abstract class CreeperEntityRendererMixin implements EntityRendererWithBabyModel {

    @Unique
    private CreeperEntityModel tiny_terrors$babyModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tiny_terrors$initBabyModel(EntityRendererFactory.Context context, CallbackInfo ci) {
        tiny_terrors$babyModel = new CreeperEntityModel(context.getPart(ModEntityModelLayers.CREEPER_BABY));
    }

    @Unique
    @Override
    public EntityModel<?> tiny_terrors$getBabyModel() {
        return tiny_terrors$babyModel;
    }
}
