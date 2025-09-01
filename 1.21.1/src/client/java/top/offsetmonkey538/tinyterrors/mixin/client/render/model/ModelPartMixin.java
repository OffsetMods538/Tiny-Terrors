package top.offsetmonkey538.tinyterrors.mixin.client.render.model;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.client.duck.ModelPartDuck;
import top.offsetmonkey538.tinyterrors.client.util.EntityRenderContext;

import java.util.ArrayList;
import java.util.List;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements ModelPartDuck {

    @Unique
    private static final float tiny_terrors$BABY_CREEPER_HEAD_Y_OFFSET = 14.0f;
    @Unique
    private static final float tiny_terrors$BABY_CREEPER_BODY_Y_OFFSET = 24.0f;


    @Unique
    private boolean isCreeperRoot = false;
    @Unique
    private boolean isHead = false;

    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;values()Ljava/util/Collection;"
            )
    )
    private void tiny_terrors$initializeBabyCreeperHeadAndBodyPartLists(
            MatrixStack matrices,
            VertexConsumer vertices,
            int light,
            int overlay,
            int color,
            CallbackInfo ci,
            @Share(value = "tiny_terrors$headPartRenderMethodList") LocalRef<List<Runnable>> headPartRenderMethodList,
            @Share(value = "tiny_terrors$bodyPartRenderMethodList") LocalRef<List<Runnable>> bodyPartRenderMethodList
    ) {
        if (!isCreeperRoot || !EntityRenderContext.isBaby.get()) return;

        headPartRenderMethodList.set(new ArrayList<>());
        bodyPartRenderMethodList.set(new ArrayList<>());
    }

    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"
            )
    )
    private void tiny_terrors$populateBabyCreeperHeadAndBodyPartLists(
            ModelPart instance,
            MatrixStack matrices,
            VertexConsumer vertices,
            int light,
            int overlay,
            int color,
            Operation<Void> original,
            @Share(value = "tiny_terrors$headPartRenderMethodList") LocalRef<List<Runnable>> headPartRenderMethodList,
            @Share(value = "tiny_terrors$bodyPartRenderMethodList") LocalRef<List<Runnable>> bodyPartRenderMethodList
    ) {
        if (!isCreeperRoot || !EntityRenderContext.isBaby.get()) {
            original.call(instance, matrices, vertices, light, overlay, color);
            return;
        }

        if (((ModelPartMixin) (Object) instance).isHead) headPartRenderMethodList.get().add(() -> original.call(instance, matrices, vertices, light, overlay, color));
        else bodyPartRenderMethodList.get().add(() -> original.call(instance, matrices, vertices, light, overlay, color));
    }

    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"
            )
    )
    private void tiny_terrors$transformAndRenderBabyCreeperHeadAndBodyParts(
            MatrixStack matrices,
            VertexConsumer vertices,
            int light,
            int overlay,
            int color,
            CallbackInfo ci,
            @Share(value = "tiny_terrors$headPartRenderMethodList") LocalRef<List<Runnable>> headPartRenderMethodList,
            @Share(value = "tiny_terrors$bodyPartRenderMethodList") LocalRef<List<Runnable>> bodyPartRenderMethodList
    ) {
        if (!isCreeperRoot || !EntityRenderContext.isBaby.get()) {
            return;
        }

        // THe head
        matrices.push();

        matrices.scale(0.75f, 0.75f, 0.75f);
        matrices.translate(0f, tiny_terrors$BABY_CREEPER_HEAD_Y_OFFSET / 16f, 0f);

        headPartRenderMethodList.get().forEach(Runnable::run);

        matrices.pop();


        // Nou da bodi
        matrices.push();

        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.translate(0.0F, tiny_terrors$BABY_CREEPER_BODY_Y_OFFSET / 16.0F, 0.0F);

        bodyPartRenderMethodList.get().forEach(Runnable::run);

        matrices.pop();
    }


    @Unique
    @Override
    public void tiny_terrors$setIsCreeperRoot() {
        this.isCreeperRoot = true;
    }
    @Unique
    @Override
    public void tiny_terrors$setIsHead() {
        this.isHead = true;
    }
}
