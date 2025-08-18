package top.offsetmonkey538.tinyterrors.mixin.entity.dummy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class DummyLivingEntityMixin extends Entity {

    @Shadow
    public abstract @Nullable EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute);

    @Shadow
    public abstract boolean isBaby();

    public DummyLivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapMethod(
            method = "isBaby"
    )
    protected boolean tinyterrors$isBaby(Operation<Boolean> original) {
        return original.call();
    }

    @WrapMethod(
            method = "onTrackedDataSet"
    )
    protected void tinyterrors$onTrackedDataSet(TrackedData<?> data, Operation<Void> original) {
        original.call(data);
    }

    @WrapMethod(
            method = "getBaseDimensions"
    )
    protected EntityDimensions tinyterrors$getBaseDimensions(EntityPose pose, Operation<EntityDimensions> original) {
        return original.call(pose);
    }
}
