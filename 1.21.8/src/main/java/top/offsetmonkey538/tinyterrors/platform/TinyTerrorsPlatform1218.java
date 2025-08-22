package top.offsetmonkey538.tinyterrors.platform;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import top.offsetmonkey538.tinyterrors.common.platform.TinyTerrorsPlatform;

public final class TinyTerrorsPlatform1218 implements TinyTerrorsPlatform {
    @Override
    public RegistryEntry<EntityAttribute> getSpeedAttributeImpl() {
        return EntityAttributes.MOVEMENT_SPEED;
    }
}
