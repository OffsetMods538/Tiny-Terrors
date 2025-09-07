package top.offsetmonkey538.tinyterrors.common.config;

import blue.endless.jankson.Comment;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import top.offsetmonkey538.offsetconfig538.api.config.Config;
import top.offsetmonkey538.offsetconfig538.api.config.Datafixer;

import java.nio.file.Path;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.MOD_ID;

public final class ModConfig implements Config {

    public BabyCreeperConfig creeperConfig = new BabyCreeperConfig();
    public BabyEndermanConfig endermanConfig = new BabyEndermanConfig();
    public BabyAbstractSkeletonConfig skeletonConfig = new BabyAbstractSkeletonConfig();
    public BabyAbstractSkeletonConfig boggedConfig = new BabyAbstractSkeletonConfig();
    public BabyAbstractSkeletonConfig strayConfig = new BabyAbstractSkeletonConfig();
    public BabyAbstractSkeletonConfig witherSkeletonConfig = new BabyAbstractSkeletonConfig();

    public static class BaseBabyMobConfig {
        @Comment("Default: 2.5")
        public double xpMultiplier = 2.5;

        @Comment("Default: 0.1")
        public double spawnChance = 0.1;

        @Comment("Default: 0.05")
        public double jockeySpawnChance = 0.05;

        public boolean shouldBeBaby(final Random random) {
            return random.nextDouble() < spawnChance;
        }

        public boolean shouldBeJockey(final Random random) {
            return random.nextDouble() < jockeySpawnChance;
        }
    }

    public static class BabyCreeperConfig extends BaseBabyMobConfig {
        @Comment("Default: 0.75")
        public double speedMultiplier = 0.75;

        @Comment("Amount of ticks until baby creeper explodes after getting close to a player. Default: 5")
        public int fuseTime = 5;
        @Comment("Default: 0.85")
        public double explosionRadiusMultiplier = 0.85;
        @Comment("Default: 0.5")
        public double igniteRadiusMultiplier = 0.5;
    }

    public static class BabyEndermanConfig extends BaseBabyMobConfig {
        @Comment("Default: 0.35")
        public double speedMultiplier = 0.35;

        @Comment("Default: false")
        public boolean canPickUpBlocks = false;
        @Comment("Default: 0.5")
        public double teleportRangeMultiplier = 0.5;
    }

    public static class BabyAbstractSkeletonConfig extends BaseBabyMobConfig {
        @Comment("Default: 0.5")
        public double speedMultiplier = 0.5;

        @Comment("Default: 0.5")
        public double bowAttackIntervalMultiplier = 0.5;
    }


    @Override
    public @Range(from = 0L, to = 2147483647L) int getConfigVersion() {
        return 1;
    }

    @Override
    public @NotNull Datafixer[] getDatafixers() {
        return new Datafixer[] {
                (original, jankson) -> {
                    // 0 -> 1
                    final JsonObject creeperConfig = original.getObject("creeperConfig");
                    if (creeperConfig == null) return;

                    final double creeperExplosionRadius = creeperConfig.getDouble("explosionRadius", 2.5);
                    final double creeperExplosionRadiusMultiplier = creeperExplosionRadius == 2.5 ? 0.85 : creeperExplosionRadius / 3;

                    creeperConfig.remove("explosionRadius");
                    creeperConfig.put("explosionRadiusMultiplier", JsonPrimitive.of(creeperExplosionRadiusMultiplier));

                    original.put("creeperConfig", creeperConfig);
                }
        };
    }

    @Override
    public @NotNull Path getFilePath() {
        return FabricLoader.getInstance().getConfigDir().resolve(getId());
    }

    @Override
    public @NotNull String getId() {
        return "%s/main.json".formatted(MOD_ID);
    }
}
