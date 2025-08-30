package top.offsetmonkey538.tinyterrors.mixin.client.render.model;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {

    @Shadow
    @Final
    public ModelPart hat;



    @ModifyExpressionValue(
            method = "getHeadParts",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;",
                    remap = false
            )
    )
    private ImmutableList<ModelPart> tiny_terrors$addHatToEndermanHeadParts(ImmutableList<ModelPart> original) {
        if (!((EntityModel<?>) (Object) this instanceof EndermanEntityModel)) return original;

        final ImmutableList.Builder<ModelPart> builder = ImmutableList.builderWithExpectedSize(original.size() + 1);
        builder.addAll(original);
        builder.add(this.hat);
        return builder.build();
    }

    @ModifyExpressionValue(
            method = "getBodyParts",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;",
                    remap = false
            )
    )
    private ImmutableList<ModelPart> tiny_terrors$removeHatFromEndermanBodyParts(ImmutableList<ModelPart> original) {
        if (!((EntityModel<?>) (Object) this instanceof EndermanEntityModel)) return original;

        final ImmutableList.Builder<ModelPart> builder = ImmutableList.builderWithExpectedSize(original.size() - 1);
        for (final ModelPart part : original) {
            if (part == this.hat) continue;
            builder.add(part);
        }
        return builder.build();
    }
}
