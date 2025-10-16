package top.offsetmonkey538.tinyterrors.common;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.offsetmonkey538.tinyterrors.common.attachment.ModAttachmentTypes;
import top.offsetmonkey538.tinyterrors.common.config.ModConfig;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;
import top.offsetmonkey538.tinyterrors.common.platform.TinyTerrorsPlatform;

import java.util.List;
import java.util.ServiceLoader;

import static top.offsetmonkey538.tinyterrors.common.attachment.ModAttachmentTypes.IS_BABY_ATTACHMENT;

// TODO: all the main source set mixins need to have data saving and loading moved to multiversion subprojects, but otherwise at least MCDev doesn't seem to complain much?
public final class TinyTerrors implements ModInitializer {
	public static final String MOD_ID = "tiny-terrors";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ConfigHolder<ModConfig> config = ConfigManager.init(ConfigHolder.create(ModConfig::new, LOGGER::error));

    public static final Identifier BABY_SPEED_MODIFIER_ID = id("baby_speed_multiplier");
    public static final String IS_BABY_KEY = "TinyTerrorsIsBaby";

	@Override
	public void onInitialize() {
        ModAttachmentTypes.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

    public static void makeJockey(final Entity entity, final ServerWorldAccess world, final LocalDifficulty difficulty) {
        // Try with existing chicken
        final List<ChickenEntity> nearbyChickens = world.getEntitiesByClass(ChickenEntity.class, entity.getBoundingBox().expand(5.0, 3.0, 5.0), EntityPredicates.NOT_MOUNTED);
        if (!nearbyChickens.isEmpty()) {
            final ChickenEntity chicken = nearbyChickens.getFirst();
            chicken.setHasJockey(true);
            entity.startRiding(chicken);
            return;
        }


        // Spawn a new chicken
        //final ChickenEntity chicken = EntityType.CHICKEN.create(entity.getWorld());
        final ChickenEntity chicken = TinyTerrorsPlatform.createChicken(entity.getWorld());
        if (chicken == null) return;

        chicken.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), 0.0F);
        chicken.initialize(world, difficulty, SpawnReason.JOCKEY, null);
        chicken.setHasJockey(true);
        entity.startRiding(chicken);
        world.spawnEntity(chicken);
    }

    public static EntityData initialize(MobEntity entity, ModConfig.BaseBabyMobConfig config, ServerWorldAccess world, LocalDifficulty difficulty, EntityData entityData) {
        final Random random = world.getRandom();

        if (!config.shouldBeBaby(random)) return entityData;
        entity.setBaby(true);

        if (config.shouldBeJockey(random)) makeJockey(entity, world, difficulty);

        return entityData;
    }

    //todo: remove once nothing uses this again
    public static void setBaby(MobEntity entity, boolean isBaby, TrackedData<Boolean> isBabyTracked, EntityAttributeModifier speedModifier) {
        entity.getDataTracker().set(isBabyTracked, isBaby);

        final World world = entity.getWorld();
        if (world == null || world.isClient) return;

        final EntityAttributeInstance movementSpeed = entity.getAttributeInstance(TinyTerrorsPlatform.getSpeedAttribute());
        if (movementSpeed == null) return; // Really shouldn't happen with movement speed but sure, it's marked Nullable

        movementSpeed.removeModifier(BABY_SPEED_MODIFIER_ID);
        if (isBaby) movementSpeed.addTemporaryModifier(speedModifier);
    }

    public static void setBaby(MobEntity entity, boolean isBaby, EntityAttributeModifier speedModifier) {
        entity.setAttached(IS_BABY_ATTACHMENT, isBaby);

        final World world = entity.getWorld();
        if (world == null || world.isClient) return;

        final EntityAttributeInstance movementSpeed = entity.getAttributeInstance(TinyTerrorsPlatform.getSpeedAttribute());
        if (movementSpeed == null) return; // Really shouldn't happen with movement speed but sure, it's marked Nullable

        movementSpeed.removeModifier(BABY_SPEED_MODIFIER_ID);
        if (isBaby) movementSpeed.addTemporaryModifier(speedModifier);
    }

    public static <T> T load(Class<T> clazz) {
        System.out.println("loading service for: " + clazz);
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }
}
