package top.offsetmonkey538.tinyterrors.mixin.entity.dummy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEntity.class)
public abstract class DummyMobEntityMixin extends DummyLivingEntityMixin {

    @Shadow
    public abstract void setBaby(boolean baby);

    @WrapMethod(
            method = "writeCustomDataToNbt"
    )
    protected void tiny_terrors$writeCustomData(NbtCompound nbt, Operation<Void> original) {
        original.call(nbt);
    }

    @WrapMethod(
            method = "readCustomDataFromNbt"
    )
    protected void tiny_terrors$readCustomData(NbtCompound nbt, Operation<Void> original) {
        original.call(nbt);
    }
}
