package top.offsetmonkey538.tinyterrors.mixin.entity.skeleton;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import top.offsetmonkey538.tinyterrors.common.entity.EntityWithBaby;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.*;


@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends DummyMobEntityMixin {

    @Override
    protected void tiny_terrors$writeCustomData(NbtCompound nbt, Operation<Void> original) {
        if (this instanceof EntityWithBaby) nbt.putBoolean(IS_BABY_KEY, this.isBaby());

        super.tiny_terrors$writeCustomData(nbt, original);
    }

    @Override
    protected void tiny_terrors$readCustomData(NbtCompound nbt, Operation<Void> original) {
        this.setBaby(nbt.getBoolean(IS_BABY_KEY));

        super.tiny_terrors$readCustomData(nbt, original);
    }
}
