package top.offsetmonkey538.tinyterrors.platform;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import top.offsetmonkey538.tinyterrors.common.platform.TinyTerrorsPlatform;

public final class TinyTerrorsPlatform1211 implements TinyTerrorsPlatform {
    @Override
    public RegistryEntry<EntityAttribute> getSpeedAttributeImpl() {
        return EntityAttributes.GENERIC_MOVEMENT_SPEED;
    }

    @Override
    public ChickenEntity createChickenImpl(World world) {
        return EntityType.CHICKEN.create(world);
    }
}
