package top.offsetmonkey538.tinyterrors.mixin.entity.skeleton;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import top.offsetmonkey538.tinyterrors.common.entity.EntityWithBaby;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.*;


@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends DummyMobEntityMixin {

    @Override
    protected void tiny_terrors$writeCustomData(WriteView view, Operation<Void> original) {
        if (this instanceof EntityWithBaby) view.putBoolean(IS_BABY_KEY, this.isBaby());

        super.tiny_terrors$writeCustomData(view, original);
    }

    @Override
    protected void tiny_terrors$readCustomData(ReadView view, Operation<Void> original) {
        this.setBaby(view.getBoolean(IS_BABY_KEY, false));

        super.tiny_terrors$readCustomData(view, original);
    }
}
