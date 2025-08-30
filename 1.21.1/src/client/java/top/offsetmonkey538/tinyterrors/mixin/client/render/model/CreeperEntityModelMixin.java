package top.offsetmonkey538.tinyterrors.mixin.client.render.model;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.tinyterrors.client.duck.ModelPartDuck;

@Mixin(CreeperEntityModel.class)
public abstract class CreeperEntityModelMixin {

    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ModelPart;getChild(Ljava/lang/String;)Lnet/minecraft/client/model/ModelPart;"
            )
    )
    private ModelPart tiny_terrors$markCreeperModelParts(ModelPart instance, String name, Operation<ModelPart> original) {
        final ModelPart childModel = original.call(instance, name);

        if (EntityModelPartNames.HEAD.equals(name)) {
            ((ModelPartDuck) (Object) childModel).tiny_terrors$setIsHead();
        }

        // instance should be root
        ((ModelPartDuck) (Object) instance).tiny_terrors$setIsCreeperRoot();

        return childModel;
    }
}
