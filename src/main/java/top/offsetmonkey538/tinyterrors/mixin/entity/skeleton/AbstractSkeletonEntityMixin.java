package top.offsetmonkey538.tinyterrors.mixin.entity.skeleton;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.TinyTerrors;
import top.offsetmonkey538.tinyterrors.entity.EntityWithBaby;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import java.util.HashSet;
import java.util.Set;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.*;

// Variant mixins need to implement the injection into 'initDataTracker' and also the 'getBaseDimensions', 'getTrackedData', 'getSpeedModifier' and 'getConfig' methods.
@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends DummyMobEntityMixin {
    public AbstractSkeletonEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract void updateAttackType();

    @Unique
    private static final Set<String> tiny_terrors$unimplementedEntities = new HashSet<>();

    @Unique
    @Nullable
    private EntityWithBaby tiny_terrors$asEntityWithBaby = null;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void tiny_terrors$checkBabyVariantDisabled(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (this instanceof EntityWithBaby entity) {
            tiny_terrors$asEntityWithBaby = entity;
            return;
        }


        final String className = getClass().getName();
        if (tiny_terrors$unimplementedEntities.contains(className)) return;
        tiny_terrors$unimplementedEntities.add(className);


        LOGGER.error("Entity '{}' extending from 'AbstractSkeletonEntity' doesn't implement 'EntityWithBaby'!", className);
        LOGGER.error("Entity will not have a baby version added!");
        LOGGER.error("This is to be expected for skeleton entities from other mods, but if this is happening with a vanilla skeleton, then something's wrong with Tiny Terrors!");
        LOGGER.error("This error will not be shown again for this entity.");
    }

    @ModifyReturnValue(
            method = {
                    "getHardAttackInterval",
                    "getRegularAttackInterval"
            },
            at = @At(value = "RETURN")
    )
    private int tiny_terrors$modifyAttackIntervalForBabyVariants(int original) {
        if (tiny_terrors$asEntityWithBaby == null || !this.isBaby()) return original;

        return (int) (original * tiny_terrors$asEntityWithBaby.tiny_terrors$getConfig().bowAttackIntervalMultiplier);
    }


    // Overrides from MobEntity
    @Override
    protected void tiny_terrors$setBaby(boolean newValue, Operation<Void> original) {
        if (tiny_terrors$asEntityWithBaby == null) {
            super.tiny_terrors$setBaby(newValue, original);
            return;
        }
        TinyTerrors.setBaby((MobEntity) (Object) this, newValue, tiny_terrors$asEntityWithBaby.tiny_terrors$getTrackedData(), tiny_terrors$asEntityWithBaby.tiny_terrors$getSpeedModifier());

        this.updateAttackType();
    }

    @Override
    protected int tiny_terrors$getExperienceToDrop(ServerWorld world, Operation<Integer> original) {
        if (tiny_terrors$asEntityWithBaby == null) return super.tiny_terrors$getExperienceToDrop(world, original);

        if (this.isBaby()) this.experiencePoints = (int) (this.experiencePoints * tiny_terrors$asEntityWithBaby.tiny_terrors$getConfig().xpMultiplier);

        return super.tiny_terrors$getExperienceToDrop(world, original);
    }

    @Override
    protected void tiny_terrors$writeCustomData(WriteView view, Operation<Void> original) {
        if (tiny_terrors$asEntityWithBaby != null) view.putBoolean(IS_BABY_KEY, this.isBaby());

        super.tiny_terrors$writeCustomData(view, original);
    }

    @Override
    protected void tiny_terrors$readCustomData(ReadView view, Operation<Void> original) {
        this.setBaby(view.getBoolean(IS_BABY_KEY, false));

        super.tiny_terrors$readCustomData(view, original);
    }

    @Override
    protected EntityData tiny_terrors$initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, Operation<EntityData> original) {
        if (tiny_terrors$asEntityWithBaby == null) return super.tiny_terrors$initialize(world, difficulty, spawnReason, entityData, original);
        return initialize((MobEntity) (Object) this, tiny_terrors$asEntityWithBaby.tiny_terrors$getConfig(), world, difficulty, super.tiny_terrors$initialize(world, difficulty, spawnReason, entityData, original));
    }


    // Overrides from LivingEntity
    @Override
    protected boolean tiny_terrors$isBaby(Operation<Boolean> original) {
        if (tiny_terrors$asEntityWithBaby == null) return super.tiny_terrors$isBaby(original);
        return this.getDataTracker().get(tiny_terrors$asEntityWithBaby.tiny_terrors$getTrackedData());
    }

    @Override
    protected void tiny_terrors$onTrackedDataSet(TrackedData<?> data, Operation<Void> original) {
        if (tiny_terrors$asEntityWithBaby != null && tiny_terrors$asEntityWithBaby.tiny_terrors$getTrackedData().equals(data)) this.calculateDimensions();

        super.tiny_terrors$onTrackedDataSet(data, original);
    }
}
