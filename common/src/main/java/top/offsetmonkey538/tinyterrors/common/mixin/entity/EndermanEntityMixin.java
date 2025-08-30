package top.offsetmonkey538.tinyterrors.common.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.common.TinyTerrors;
import top.offsetmonkey538.tinyterrors.common.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.*;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends DummyMobEntityMixin {
    public EndermanEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private static final EntityDimensions tiny_terrors$BABY_DIMENSIONS = EntityType.ENDERMAN.getDimensions().scaled(0.6F).withEyeHeight(1.3F);

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> tiny_terrors$BABY = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final EntityAttributeModifier tiny_terrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().endermanConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );

    @Unique
    private Goal tiny_terros$pickUpBlockGoal;

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void tiny_terrors$addBabyData(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(tiny_terrors$BABY, false);
    }

    @ModifyExpressionValue(
            method = "initGoals",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/entity/mob/EndermanEntity;)Lnet/minecraft/entity/mob/EndermanEntity$PickUpBlockGoal;"
            )
    )
    private @Coerce Goal tiny_terrors$capturePickUpBlockGoal(@Coerce Goal original) {
        tiny_terros$pickUpBlockGoal = original;
        return original;
    }

    @SuppressWarnings("MixinAnnotationTarget") // MCDev doesn't understand that not all target methods need all the at targets
    @ModifyExpressionValue(
            method = {
                    "teleportRandomly",
                    "teleportTo(Lnet/minecraft/entity/Entity;)Z"
            },
            at = {
                    @At(
                            value = "CONSTANT",
                            args = "doubleValue=64.0"
                    ),
                    @At(
                            value = "CONSTANT",
                            args = "doubleValue=16.0"
                    )
            }
    )
    private double tiny_terrors$double$modifyTeleportRange(double original) {
        return isBaby() ? original * config.get().endermanConfig.teleportRangeMultiplier : original;
    }
    @SuppressWarnings("MixinAnnotationTarget") // MCDev doesn't understand that not all target methods need all the at targets
    @ModifyExpressionValue(
            method = {
                    "teleportRandomly",
                    "teleportTo(Lnet/minecraft/entity/Entity;)Z"
            },
            at = {
                    @At(
                            value = "CONSTANT",
                            args = "intValue=64"
                    ),
                    @At(
                            value = "CONSTANT",
                            args = "intValue=32"
                    ),
                    @At(
                            value = "CONSTANT",
                            args = "intValue=16"
                    )
            }
    )
    private int tiny_terrors$int$modifyTeleportRange(int original) {
        return isBaby() ? (int) (original * config.get().endermanConfig.teleportRangeMultiplier) : original;
    }


    // Overrides from MobEntity
    @Override
    protected void tiny_terrors$setBaby(boolean newValue, Operation<Void> original) {
        if (newValue && !config.get().endermanConfig.canPickUpBlocks) goalSelector.remove(tiny_terros$pickUpBlockGoal);
        TinyTerrors.setBaby((MobEntity) (Object) this, newValue, tiny_terrors$BABY, tiny_terrors$BABY_SPEED_BONUS);
    }

    @Override
    protected int tiny_terrors$getExperienceToDrop(int original) {
        if (this.isBaby()) this.experiencePoints = (int) (this.experiencePoints * config.get().endermanConfig.xpMultiplier);

        return super.tiny_terrors$getExperienceToDrop(original);
    }

    @Override
    protected EntityData tiny_terrors$initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, Operation<EntityData> original) {
        return initialize((MobEntity) (Object) this, config.get().endermanConfig, world, difficulty, super.tiny_terrors$initialize(world, difficulty, spawnReason, entityData, original));
    }


    // Overrides from LivingEntity
    @Override
    protected boolean tiny_terrors$isBaby(Operation<Boolean> original) {
        return this.getDataTracker().get(tiny_terrors$BABY);
    }

    @Override
    protected void tiny_terrors$onTrackedDataSet(TrackedData<?> data, Operation<Void> original) {
        if (tiny_terrors$BABY.equals(data)) this.calculateDimensions();

        super.tiny_terrors$onTrackedDataSet(data, original);
    }

    @Override
    protected EntityDimensions tiny_terrors$getBaseDimensions(EntityPose pose, Operation<EntityDimensions> original) {
        return this.isBaby() ? tiny_terrors$BABY_DIMENSIONS : super.tiny_terrors$getBaseDimensions(pose, original);
    }
}
