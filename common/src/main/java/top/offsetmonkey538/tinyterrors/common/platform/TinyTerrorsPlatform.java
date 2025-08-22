package top.offsetmonkey538.tinyterrors.common.platform;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.load;

public interface TinyTerrorsPlatform {
    TinyTerrorsPlatform INSTANCE = load(TinyTerrorsPlatform.class);


    static RegistryEntry<EntityAttribute> getSpeedAttribute() {
        return INSTANCE.getSpeedAttributeImpl();
    }


    RegistryEntry<EntityAttribute> getSpeedAttributeImpl();
}
