package top.offsetmonkey538.tinyterrors.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.tinyterrors.entity.EntityWithBaby;
import top.offsetmonkey538.tinyterrors.mixin.entity.dummy.DummyMobEntityMixin;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.*;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends DummyMobEntityMixin implements EntityWithBaby {
    public CreeperEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Unique
    private static final EntityDimensions tinyterrors$BABY_DIMENSIONS = EntityType.ZOMBIE.getDimensions().scaled(0.5F).withEyeHeight(0.8F);

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> tinyterrors$BABY = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final EntityAttributeModifier tinyterrors$BABY_SPEED_BONUS = new EntityAttributeModifier(
            BABY_SPEED_MODIFIER_ID, config.get().creeperConfig.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void tinyterrors$addBabyData(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(tinyterrors$BABY, false);
    }


    // Overrides from MobEntity
    @Override
    protected void tinyterrors$setBaby(boolean newValue, Operation<Void> original) {
        this.getDataTracker().set(tinyterrors$BABY, newValue);
        
        final World world = this.getWorld();
        if (world == null || world.isClient) return;
        

        final EntityAttributeInstance movementSpeed = this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (movementSpeed == null) return; // Really shouldn't happen with movement speed but sure, it's marked Nullable

        movementSpeed.removeModifier(BABY_SPEED_MODIFIER_ID);
        if (newValue) movementSpeed.addTemporaryModifier(tinyterrors$BABY_SPEED_BONUS);
    }

    @Override
    protected int tinyterrors$getExperienceToDrop(ServerWorld world, Operation<Integer> original) {
        if (this.isBaby()) this.experiencePoints = (int) (this.experiencePoints * config.get().creeperConfig.xpMultiplier);

        return super.tinyterrors$getExperienceToDrop(world, original);
    }

    @Override
    protected void tinyterrors$writeCustomData(WriteView view, Operation<Void> original) {
        view.putBoolean(IS_BABY_KEY, this.isBaby());

        super.tinyterrors$writeCustomData(view, original);
    }

    @Override
    protected void tinyterrors$readCustomData(ReadView view, Operation<Void> original) {
        this.setBaby(view.getBoolean(IS_BABY_KEY, false));

        super.tinyterrors$readCustomData(view, original);
    }


    // Overrides from LivingEntity
    @Override
    protected boolean tinyterrors$isBaby(Operation<Boolean> original) {
        return this.getDataTracker().get(tinyterrors$BABY);
    }

    @Override
    protected void tinyterrors$onTrackedDataSet(TrackedData<?> data, Operation<Void> original) {
        if (tinyterrors$BABY.equals(data)) this.calculateDimensions();

        super.tinyterrors$onTrackedDataSet(data, original);
    }

    @Override
    protected EntityDimensions tinyterrors$getBaseDimensions(EntityPose pose, Operation<EntityDimensions> original) {
        return this.isBaby() ? tinyterrors$BABY_DIMENSIONS : super.tinyterrors$getBaseDimensions(pose, original);
    }
}
