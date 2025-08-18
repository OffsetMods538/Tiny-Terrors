package top.offsetmonkey538.tinyterrors.mixin.entity.dummy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEntity.class)
public abstract class DummyMobEntityMixin extends DummyLivingEntityMixin {

    @Shadow
    protected int experiencePoints;

    @Shadow
    public abstract void setBaby(boolean baby);

    public DummyMobEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapMethod(
            method = "setBaby"
    )
    protected void tiny_terrors$setBaby(boolean newValue, Operation<Void> original) {
        original.call(newValue);
    }

    @WrapMethod(
            method = "getExperienceToDrop"
    )
    protected int tiny_terrors$getExperienceToDrop(ServerWorld world, Operation<Integer> original) {
        return original.call(world);
    }

    @WrapMethod(
            method = "writeCustomData"
    )
    protected void tiny_terrors$writeCustomData(WriteView view, Operation<Void> original) {
        original.call(view);
    }

    @WrapMethod(
            method = "readCustomData"
    )
    protected void tiny_terrors$readCustomData(ReadView view, Operation<Void> original) {
        original.call(view);
    }

    @WrapMethod(
            method = "initialize"
    )
    protected EntityData tiny_terrors$initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, Operation<EntityData> original) {
        return original.call(world, difficulty, spawnReason, entityData);
    }
}
