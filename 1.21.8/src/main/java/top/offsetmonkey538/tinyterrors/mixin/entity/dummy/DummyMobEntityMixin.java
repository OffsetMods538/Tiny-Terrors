package top.offsetmonkey538.tinyterrors.mixin.entity.dummy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEntity.class)
public abstract class DummyMobEntityMixin extends DummyLivingEntityMixin {

    @Shadow
    public abstract void setBaby(boolean baby);

    @WrapMethod(
            method = "writeCustomData"
    )
    protected void tiny_terrors$writeCustomData(WriteView view, Operation<Void> original) {
        original.call(view);
    }

    @WrapMethod(
            method = "readCustomData"
    )
    protected void tiny_terrors$readCustomData(ReadView view, Operation<Void> original) {
        original.call(view);
    }
}
