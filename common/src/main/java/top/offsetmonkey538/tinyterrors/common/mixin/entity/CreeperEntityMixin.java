package top.offsetmonkey538.tinyterrors.common.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.common.TinyTerrors;
import top.offsetmonkey538.tinyterrors.common.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.*;
import static top.offsetmonkey538.tinyterrors.common.attachment.ModAttachmentTypes.IS_BABY_ATTACHMENT;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends DummyMobEntityMixin {
    public CreeperEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    private int fuseTime;


    @Unique
    private static final EntityDimensions tiny_terrors$BABY_DIMENSIONS = EntityType.CREEPER.getDimensions().scaled(0.5F).withEyeHeight(0.8F);

    @Unique
    private static final EntityAttributeModifier tiny_terrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().creeperConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    @SuppressWarnings("UnstableApiUsage")
    private void tiny_terrors$addIsBabyAttachmentChangeListener(EntityType<?> entityType, World world, CallbackInfo ci) {
        this.onAttachedSet(IS_BABY_ATTACHMENT).register((oldValue, newValue) -> {
            if (newValue) this.fuseTime = config.get().creeperConfig.fuseTime;

            this.calculateDimensions();
        });
    }

    @ModifyVariable(
            method = "explode",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            )
    )
    private float tiny_terrors$setBabyExplosionRadius(float original) {
        if (!isBaby()) return original;
        return (float) (original * config.get().creeperConfig.explosionRadiusMultiplier);
    }


    // Overrides from MobEntity
    @Override
    protected void tiny_terrors$setBaby(boolean newValue, Operation<Void> original) {
        TinyTerrors.setBaby((MobEntity) (Object) this, newValue, tiny_terrors$BABY_SPEED_BONUS);
    }

    @Override
    protected int tiny_terrors$getExperienceToDrop(int original) {
        if (this.isBaby()) this.experiencePoints = (int) (this.experiencePoints * config.get().creeperConfig.xpMultiplier);

        return super.tiny_terrors$getExperienceToDrop(original);
    }

    @Override
    protected EntityData tiny_terrors$initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, Operation<EntityData> original) {
        return initialize((MobEntity) (Object) this, config.get().creeperConfig, world, difficulty, super.tiny_terrors$initialize(world, difficulty, spawnReason, entityData, original));
    }


    // Overrides from LivingEntity
    @Override
    protected boolean tiny_terrors$isBaby(Operation<Boolean> original) {
        // TODO: may be able to use this implementation at the LivingEntity level. Though it may not be a good idea to check if the attachment exists on every LivingEntity?
        return this.getAttachedOrElse(IS_BABY_ATTACHMENT, false);
    }

    @Override
    protected EntityDimensions tiny_terrors$getBaseDimensions(EntityPose pose, Operation<EntityDimensions> original) {
        return this.isBaby() ? tiny_terrors$BABY_DIMENSIONS : super.tiny_terrors$getBaseDimensions(pose, original);
    }
}
