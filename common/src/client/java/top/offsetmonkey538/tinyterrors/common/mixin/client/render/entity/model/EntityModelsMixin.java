package top.offsetmonkey538.tinyterrors.common.mixin.client.render.entity.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;

@Mixin(EntityModels.class)
public interface EntityModelsMixin {

    @Accessor
    static Dilation getARMOR_DILATION() {
        throw new MixinApplyError("EntityModelsMixin from tiny-terrors wasn't applied!");
    }

    @Accessor
    static Dilation getHAT_DILATION() {
        throw new MixinApplyError("EntityModelsMixin from tiny-terrors wasn't applied!");
    }
}
