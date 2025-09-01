package top.offsetmonkey538.tinyterrors.common.mixin.entity.dummy;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class DummyMobEntityMixin extends DummyLivingEntityMixin {

    @Shadow
    protected int experiencePoints;

    @Shadow @Final protected GoalSelector goalSelector;

    public DummyMobEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // This is only for Stray to implement as it doesn't implement initDataTracker
    @WrapMethod(
            method = "initDataTracker"
    )
    protected void tiny_terrors$initDataTracker(DataTracker.Builder builder, Operation<Void> original) {
        original.call(builder);
    }
    @WrapMethod(
            method = "setBaby"
    )
    protected void tiny_terrors$setBaby(boolean newValue, Operation<Void> original) {
        original.call(newValue);
    }

    @ModifyReturnValue(
            method = {
                    // 1.21.4+
                    "getExperienceToDrop(Lnet/minecraft/server/world/ServerWorld;)I",
                    "getBaseExperienceReward(Lnet/minecraft/server/level/ServerLevel;)I",
                    "method_6110(Lnet/minecraft/class_3218;)I",

                    // 1.21.2+
                    "getXpToDrop(Lnet/minecraft/server/world/ServerWorld;)I",
                    "getBaseExperienceReward(Lnet/minecraft/server/level/ServerLevel;)I",
                    "method_6110(Lnet/minecraft/class_3218;)I",

                    // 1.21.1-
                    "getXpToDrop()I",
                    "getBaseExperienceReward()I",
                    "method_6110()I"
            },
            at = @At("RETURN"),
            remap = false
    )
    protected int tiny_terrors$getExperienceToDrop(int original) {
        return original;
    }

    @WrapMethod(
            method = "initialize"
    )
    protected EntityData tiny_terrors$initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, Operation<EntityData> original) {
        return original.call(world, difficulty, spawnReason, entityData);
    }
}
