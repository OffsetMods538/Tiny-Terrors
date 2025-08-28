package top.offsetmonkey538.tinyterrors.mixin.entity.dummy;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class DummyLivingEntityMixin {

    @Shadow
    public abstract boolean isBaby();
}
