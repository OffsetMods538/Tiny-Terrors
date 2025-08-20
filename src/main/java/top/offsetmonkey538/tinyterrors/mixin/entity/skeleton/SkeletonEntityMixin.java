package top.offsetmonkey538.tinyterrors.mixin.entity.skeleton;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.config.ModConfig;
import top.offsetmonkey538.tinyterrors.entity.EntityWithBaby;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.*;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends DummyMobEntityMixin implements EntityWithBaby {
    public SkeletonEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private static final EntityDimensions tiny_terrors$BABY_DIMENSIONS = EntityType.SKELETON.getDimensions().scaled(0.5F).withEyeHeight(0.93F);

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> tiny_terrors$BABY = DataTracker.registerData(SkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final EntityAttributeModifier tiny_terrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().skeletonConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );


    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void tiny_terrors$addBabyData(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(tiny_terrors$BABY, false);
    }


    @Override
    protected EntityDimensions tiny_terrors$getBaseDimensions(EntityPose pose, Operation<EntityDimensions> original) {
        return this.isBaby() ? tiny_terrors$BABY_DIMENSIONS : super.tiny_terrors$getBaseDimensions(pose, original);
    }

    @Unique
    @Override
    public TrackedData<Boolean> tiny_terrors$getTrackedData() {
        return tiny_terrors$BABY;
    }

    @Unique
    @Override
    public EntityAttributeModifier tiny_terrors$getSpeedModifier() {
        return tiny_terrors$BABY_SPEED_BONUS;
    }

    @Unique
    @Override
    public ModConfig.BabyAbstractSkeletonConfig tiny_terrors$getConfig() {
        return config.get().skeletonConfig;
    }
}
