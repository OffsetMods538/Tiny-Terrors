package top.offsetmonkey538.tinyterrors.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.*;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends DummyMobEntityMixin {
    public EndermanEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private static final EntityDimensions tiny_terrors$BABY_DIMENSIONS = EntityType.ENDERMAN.getDimensions().scaled(0.5F).withEyeHeight(1.3F);

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> tiny_terrors$BABY = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final EntityAttributeModifier tiny_terrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().endermanConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void tiny_terrors$addBabyData(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(tiny_terrors$BABY, false);
    }


    // Overrides from MobEntity
    @Override
    protected void tiny_terrors$setBaby(boolean newValue, Operation<Void> original) {
        this.getDataTracker().set(tiny_terrors$BABY, newValue);
        
        final World world = this.getWorld();
        if (world == null || world.isClient) return;
        

        final EntityAttributeInstance movementSpeed = this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (movementSpeed == null) return; // Really shouldn't happen with movement speed but sure, it's marked Nullable

        movementSpeed.removeModifier(BABY_SPEED_MODIFIER_ID);
        if (newValue) movementSpeed.addTemporaryModifier(tiny_terrors$BABY_SPEED_BONUS);
    }

    @Override
    protected int tiny_terrors$getExperienceToDrop(ServerWorld world, Operation<Integer> original) {
        if (this.isBaby()) this.experiencePoints = (int) (this.experiencePoints * config.get().endermanConfig.xpMultiplier);

        return super.tiny_terrors$getExperienceToDrop(world, original);
    }

    @Override
    protected void tiny_terrors$writeCustomData(WriteView view, Operation<Void> original) {
        view.putBoolean(IS_BABY_KEY, this.isBaby());

        super.tiny_terrors$writeCustomData(view, original);
    }

    @Override
    protected void tiny_terrors$readCustomData(ReadView view, Operation<Void> original) {
        this.setBaby(view.getBoolean(IS_BABY_KEY, false));

        super.tiny_terrors$readCustomData(view, original);
    }

    @Override
    protected EntityData tiny_terrors$initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, Operation<EntityData> original) {
        final Random random = world.getRandom();

        entityData = super.tiny_terrors$initialize(world, difficulty, spawnReason, entityData, original);

        if (!config.get().endermanConfig.shouldBeBaby(random)) return entityData;
        this.setBaby(true);

        if (config.get().endermanConfig.shouldBeJockey(random)) makeJockey(this, world, difficulty);

        return entityData;
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
