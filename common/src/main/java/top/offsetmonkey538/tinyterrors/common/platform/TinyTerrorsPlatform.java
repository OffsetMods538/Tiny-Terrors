package top.offsetmonkey538.tinyterrors.common.platform;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.load;

public interface TinyTerrorsPlatform {
    TinyTerrorsPlatform INSTANCE = load(TinyTerrorsPlatform.class);


    static RegistryEntry<EntityAttribute> getSpeedAttribute() {
        return INSTANCE.getSpeedAttributeImpl();
    }
    static ChickenEntity createChicken(World world) {
        return INSTANCE.createChickenImpl(world);
    }


    RegistryEntry<EntityAttribute> getSpeedAttributeImpl();
    ChickenEntity createChickenImpl(World world);
}
