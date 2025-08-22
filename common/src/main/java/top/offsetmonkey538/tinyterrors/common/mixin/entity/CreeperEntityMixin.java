package top.offsetmonkey538.tinyterrors.common.mixin.entity;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.common.TinyTerrors;
import top.offsetmonkey538.tinyterrors.common.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.*;

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
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> tiny_terrors$BABY = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final EntityAttributeModifier tiny_terrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().creeperConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void tiny_terrors$addBabyData(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(tiny_terrors$BABY, false);
    }

    @Definition(id = "explosionRadius", field = "Lnet/minecraft/entity/mob/CreeperEntity;explosionRadius:I")
    @Expression("(float) this.explosionRadius")
    @ModifyExpressionValue(
            method = "explode",
            at = @At(
                    "MIXINEXTRAS:EXPRESSION"
            )
    )
    private float tiny_terrors$setBabyExplosionRadius(float original) {
        return isBaby() ? (float) config.get().creeperConfig.explosionRadius : original;
    }


    // Overrides from MobEntity
    @Override
    protected void tiny_terrors$setBaby(boolean newValue, Operation<Void> original) {
        if (newValue) this.fuseTime = config.get().creeperConfig.fuseTime;
        TinyTerrors.setBaby((MobEntity) (Object) this, newValue, tiny_terrors$BABY, tiny_terrors$BABY_SPEED_BONUS);
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
