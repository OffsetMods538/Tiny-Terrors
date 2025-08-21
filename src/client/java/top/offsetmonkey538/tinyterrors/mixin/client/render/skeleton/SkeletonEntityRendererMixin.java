package top.offsetmonkey538.tinyterrors.mixin.client.render.skeleton;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.render.EntityRendererWithArmorLayersWithBabyModel;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;

@Mixin(SkeletonEntityRenderer.class)
public abstract class SkeletonEntityRendererMixin implements EntityRendererWithArmorLayersWithBabyModel {

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
        tiny_terrors$babyInnerArmorLayerModel = new SkeletonEntityModel<>(context.getPart(ModEntityModelLayers.SKELETON_BABY_INNER));
        tiny_terrors$babyOuterArmorLayerModel = new SkeletonEntityModel<>(context.getPart(ModEntityModelLayers.SKELETON_BABY_OUTER));
    }

    @Unique
    @Override
    public EntityModel<?> tiny_terrors$getBabyModel() {
        return tiny_terrors$babyModel;
    }
    @Unique
    @Override
    public @NotNull EntityModel<?> tiny_terrors$getInnerLayerModel() {
        return tiny_terrors$babyInnerArmorLayerModel;
    }
    @Unique
    @Override
    public @NotNull EntityModel<?> tiny_terrors$getOuterLayerModel() {
        return tiny_terrors$babyOuterArmorLayerModel;
    }
}
