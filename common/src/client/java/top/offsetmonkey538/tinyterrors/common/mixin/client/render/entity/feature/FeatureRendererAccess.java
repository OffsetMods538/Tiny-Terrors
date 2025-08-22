package top.offsetmonkey538.tinyterrors.common.mixin.client.render.entity.feature;

import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FeatureRenderer.class)
public interface FeatureRendererAccess {

    @Accessor
    FeatureRendererContext<?, ?> getContext();
}
