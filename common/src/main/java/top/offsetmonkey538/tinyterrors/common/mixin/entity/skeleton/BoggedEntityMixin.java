package top.offsetmonkey538.tinyterrors.common.mixin.entity.skeleton;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.common.config.ModConfig;
import top.offsetmonkey538.tinyterrors.common.entity.EntityWithBaby;
import top.offsetmonkey538.tinyterrors.common.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.BABY_SPEED_MODIFIER_ID;
import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.config;

@Mixin(BoggedEntity.class)
public abstract class BoggedEntityMixin extends DummyMobEntityMixin implements EntityWithBaby {
    public BoggedEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private static final EntityDimensions tiny_terrors$BABY_DIMENSIONS = EntityType.BOGGED.getDimensions().scaled(0.5F).withEyeHeight(0.93F);

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> tiny_terrors$BABY = DataTracker.registerData(BoggedEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final EntityAttributeModifier tiny_terrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().boggedConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );


    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void tiny_terrors$addBabyData(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(tiny_terrors$BABY, false);
    }

    @ModifyReturnValue(
            method = {
                    "getHardAttackInterval",
                    "getRegularAttackInterval"
            },
            at = @At(value = "RETURN")
    )
    private int tiny_terrors$modifyAttackIntervalForBabyVariants(int original) {
        if (!this.isBaby()) return original;

        return (int) (original * tiny_terrors$getConfig().bowAttackIntervalMultiplier);
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
        return config.get().boggedConfig;
    }
}
