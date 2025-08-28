package top.offsetmonkey538.tinyterrors.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.*;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends DummyMobEntityMixin {
    @Override
    protected void tiny_terrors$writeCustomData(NbtCompound nbt, Operation<Void> original) {
        nbt.putBoolean(IS_BABY_KEY, this.isBaby());

        super.tiny_terrors$writeCustomData(nbt, original);
    }

    @Override
    protected void tiny_terrors$readCustomData(NbtCompound nbt, Operation<Void> original) {
        this.setBaby(nbt.getBoolean(IS_BABY_KEY));

        super.tiny_terrors$readCustomData(nbt, original);
    }
}
