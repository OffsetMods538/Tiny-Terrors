package top.offsetmonkey538.tinyterrors.mixin.client.render;

import net.minecraft.client.render.entity.AbstractSkeletonEntityRenderer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.offsetmonkey538.tinyterrors.client.render.EntityRendererWithBabyModel;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;
import top.offsetmonkey538.tinyterrors.mixin.client.render.dummy.DummyLivingEntityRendererMixin;

@Mixin(AbstractSkeletonEntityRenderer.class)
public abstract class AbstractSkeletonEntityRendererMixin implements EntityRendererWithBabyModel {

    @Unique
    private SkeletonEntityModel<?> tiny_terrors$babyModel;

    @Inject(
            method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/EntityModelLayer;Lnet/minecraft/client/render/entity/model/EntityModelLayer;Lnet/minecraft/client/render/entity/model/SkeletonEntityModel;)V",
            at = @At("TAIL")
    )
    private void tiny_terrors$initBabyModel(EntityRendererFactory.Context context, EntityModelLayer armorInnerLayer, EntityModelLayer armorOuterLayer, SkeletonEntityModel<?> model, CallbackInfo ci) {
        tiny_terrors$babyModel = new SkeletonEntityModel<>(context.getPart(ModEntityModelLayers.SKELETON_BABY));
    }

    @Unique
    @Override
    public EntityModel<?> tiny_terrors$getBabyModel() {
        return tiny_terrors$babyModel;
    }
}
