package top.offsetmonkey538.tinyterrors.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import org.jetbrains.annotations.NotNull;

public interface EntityRendererWithArmorLayersWithBabyModel extends EntityRendererWithBabyModel {
    @NotNull EntityModel<?> tiny_terrors$getInnerLayerModel();
    @NotNull EntityModel<?> tiny_terrors$getOuterLayerModel();
}
