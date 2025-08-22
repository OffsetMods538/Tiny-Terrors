package top.offsetmonkey538.tinyterrors.common.client.render;

import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SkeletonEntityRenderer extends EntityRendererWithBabyModel {
    @NotNull SkeletonEntityModel<?> tiny_terrors$getInnerArmorModel();
    @NotNull SkeletonEntityModel<?> tiny_terrors$getOuterArmorModel();
    @Nullable SkeletonEntityModel<?> tiny_terrors$getOverlayModel();
}
