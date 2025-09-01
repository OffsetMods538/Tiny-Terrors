package top.offsetmonkey538.tinyterrors.common.entity;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.TrackedData;
import top.offsetmonkey538.tinyterrors.common.config.ModConfig;

public interface EntityWithBaby {
    TrackedData<Boolean> tiny_terrors$getTrackedData();
    EntityAttributeModifier tiny_terrors$getSpeedModifier();
    ModConfig.BabyAbstractSkeletonConfig tiny_terrors$getConfig();
}
