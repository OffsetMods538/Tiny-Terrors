package top.offsetmonkey538.tinyterrors.mixin.client.render.model;

import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(AnimalModel.class)
public abstract class AnimalModelMixin {

    @Mutable
    @Shadow
    @Final
    private float childHeadYOffset;

    @Inject(
            method = "<init>(Ljava/util/function/Function;ZFFFFF)V",
            at = @At("TAIL")
    )
    private void tiny_terrors$setBabyEndermanHeadYOffset(Function<?, ?> renderLayerFactory, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, float childBodyYOffset, CallbackInfo ci) {
        if (!((EntityModel<?>) (Object) this instanceof EndermanEntityModel)) return;
        this.childHeadYOffset = 20.5f;
    }
}
