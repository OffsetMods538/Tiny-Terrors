package top.offsetmonkey538.tinyterrors.mixin.client.render.feature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.tinyterrors.client.render.EntityRendererWithArmorLayersWithBabyModel;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {

    @ModifyExpressionValue(
            method = "getModel",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/feature/ArmorFeatureRenderer;babyInnerModel:Lnet/minecraft/client/render/entity/model/BipedEntityModel;"
            )
    )
    private BipedEntityModel<?> tiny_terrors$useBabyInnerArmorLayer(BipedEntityModel<?> original) {
        if (!(((FeatureRendererAccess) this).getContext() instanceof EntityRendererWithArmorLayersWithBabyModel withBabyModel)) return original;

        return (BipedEntityModel<?>) withBabyModel.tiny_terrors$getInnerLayerModel();
    }

    @ModifyExpressionValue(
            method = "getModel",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/feature/ArmorFeatureRenderer;babyOuterModel:Lnet/minecraft/client/render/entity/model/BipedEntityModel;"
            )
    )
    private BipedEntityModel<?> tiny_terrors$useBabyOuterArmorLayer(BipedEntityModel<?> original) {
        if (!(((FeatureRendererAccess) this).getContext() instanceof EntityRendererWithArmorLayersWithBabyModel withBabyModel)) return original;

        return (BipedEntityModel<?>) withBabyModel.tiny_terrors$getOuterLayerModel();
    }
}
