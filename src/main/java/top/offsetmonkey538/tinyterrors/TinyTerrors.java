package top.offsetmonkey538.tinyterrors;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.offsetmonkey538.tinyterrors.config.ModConfig;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;

import java.util.List;

public class TinyTerrors implements ModInitializer {
	public static final String MOD_ID = "tiny-terrors";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ConfigHolder<ModConfig> config = ConfigManager.init(ConfigHolder.create(ModConfig::new, LOGGER::error));

    public static final Identifier BABY_SPEED_MODIFIER_ID = id("baby_speed_multiplier");
    public static final String IS_BABY_KEY = "TinyTerrorsIsBaby";

	@Override
	public void onInitialize() {

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
        final ChickenEntity chicken = EntityType.CHICKEN.create(entity.getWorld(), SpawnReason.JOCKEY);
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

    public static void setBaby(MobEntity entity, boolean isBaby, TrackedData<Boolean> isBabyTracked, EntityAttributeModifier speedModifier) {
        entity.getDataTracker().set(isBabyTracked, isBaby);

        final World world = entity.getWorld();
        if (world == null || world.isClient) return;

        final EntityAttributeInstance movementSpeed = entity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (movementSpeed == null) return; // Really shouldn't happen with movement speed but sure, it's marked Nullable

        movementSpeed.removeModifier(BABY_SPEED_MODIFIER_ID);
        if (isBaby) movementSpeed.addTemporaryModifier(speedModifier);
    }
}
