package top.offsetmonkey538.tinyterrors.mixin.entity.skeleton;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.config.ModConfig;
import top.offsetmonkey538.tinyterrors.entity.EntityWithBaby;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.BABY_SPEED_MODIFIER_ID;
import static top.offsetmonkey538.tinyterrors.TinyTerrors.config;

@Mixin(StrayEntity.class)
public abstract class StrayEntityMixin extends DummyMobEntityMixin implements EntityWithBaby {
    public StrayEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private static final EntityDimensions tiny_terrors$BABY_DIMENSIONS = EntityType.STRAY.getDimensions().scaled(0.5F).withEyeHeight(0.93F);

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> tiny_terrors$BABY = DataTracker.registerData(StrayEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final EntityAttributeModifier tiny_terrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().strayConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );

    @Override
    protected void tiny_terrors$initDataTracker(DataTracker.Builder builder, Operation<Void> original) {
        super.tiny_terrors$initDataTracker(builder, original);
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
        return config.get().strayConfig;
    }
}
