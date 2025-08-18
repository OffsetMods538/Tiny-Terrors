package top.offsetmonkey538.tinyterrors.mixin.client.render.feature;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.entity.feature.CreeperChargeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;
import top.offsetmonkey538.tinyterrors.util.CreeperSwirlRenderContext;

@Mixin(CreeperChargeFeatureRenderer.class)
public abstract class CreeperChargeFeatureRendererMixin {

    @Unique
    private CreeperEntityModel tinyterrors$babyModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tinyterrors$initBabyModel(FeatureRendererContext<CreeperEntityRenderState, CreeperEntityModel> context, LoadedEntityModels loader, CallbackInfo ci) {
        tinyterrors$babyModel = new CreeperEntityModel(loader.getModelPart(ModEntityModelLayers.CREEPER_BABY_ARMOR));
    }

    @ModifyReturnValue(
            method = "getEnergySwirlModel()Lnet/minecraft/client/render/entity/model/CreeperEntityModel;",
            at = @At("RETURN")
    )
    private CreeperEntityModel tinyterrors$useBabyModel(CreeperEntityModel original) {
        if (CreeperSwirlRenderContext.isBaby.get()) return tinyterrors$babyModel;
        return original;
    }
}
