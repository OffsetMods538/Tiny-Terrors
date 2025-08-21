package top.offsetmonkey538.tinyterrors.mixin.client.render.entity.skeleton;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.SkeletonEntityRenderer;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;

@Mixin(net.minecraft.client.render.entity.SkeletonEntityRenderer.class)
public abstract class SkeletonEntityRendererMixin implements SkeletonEntityRenderer {

    @Unique
    private SkeletonEntityModel<?> tiny_terrors$babyModel;
    @Unique
    private SkeletonEntityModel<?> tiny_terrors$babyInnerArmorLayerModel;
    @Unique
    private SkeletonEntityModel<?> tiny_terrors$babyOuterArmorLayerModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tiny_terrors$initBabyModel(EntityRendererFactory.Context context, CallbackInfo ci) {
        tiny_terrors$babyModel = new SkeletonEntityModel<>(context.getPart(ModEntityModelLayers.SKELETON_BABY));
        tiny_terrors$babyInnerArmorLayerModel = new SkeletonEntityModel<>(context.getPart(ModEntityModelLayers.SKELETON_BABY_INNER_ARMOR));
        tiny_terrors$babyOuterArmorLayerModel = new SkeletonEntityModel<>(context.getPart(ModEntityModelLayers.SKELETON_BABY_OUTER_ARMOR));
    }

    @Unique
    @Override
    public EntityModel<?> tiny_terrors$getBabyModel() {
        return tiny_terrors$babyModel;
    }
    @Unique
    @Override
    public @NotNull SkeletonEntityModel<?> tiny_terrors$getInnerArmorModel() {
        return tiny_terrors$babyInnerArmorLayerModel;
    }
    @Unique
    @Override
    public @NotNull SkeletonEntityModel<?> tiny_terrors$getOuterArmorModel() {
        return tiny_terrors$babyOuterArmorLayerModel;
    }
    @Unique
    @Override
    public @Nullable SkeletonEntityModel<?> tiny_terrors$getOverlayModel() {
        return null;
    }
}
