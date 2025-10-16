package top.offsetmonkey538.tinyterrors.platform;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import top.offsetmonkey538.tinyterrors.common.platform.TinyTerrorsPlatform;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.config;
import static top.offsetmonkey538.tinyterrors.common.attachment.ModAttachmentTypes.IS_BABY_ATTACHMENT;

public final class TinyTerrorsPlatform1211 implements TinyTerrorsPlatform {
    @Override
    public RegistryEntry<EntityAttribute> getSpeedAttributeImpl() {
        return EntityAttributes.GENERIC_MOVEMENT_SPEED;
    }

    @Override
    public ChickenEntity createChickenImpl(World world) {
        var e = new AllayEntity(EntityType.ALLAY, null);

        e.getAttached(IS_BABY_ATTACHMENT).register((oldValue, newValue) -> {
            if (newValue) this.fuseTime = config.get().creeperConfig.fuseTime;

            this.calculateDimensions();
        });

        return EntityType.CHICKEN.create(world);
    }
}
