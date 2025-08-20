package top.offsetmonkey538.tinyterrors.config;

import blue.endless.jankson.Comment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.offsetconfig538.api.config.Config;

import java.nio.file.Path;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.MOD_ID;

public final class ModConfig implements Config {

    public BabyCreeperConfig creeperConfig = new BabyCreeperConfig();
    public BabyEndermanConfig endermanConfig = new BabyEndermanConfig();

    public static class BaseBabyMobConfig {
        @Comment("Default: 0.5")
        public double xpMultiplier = 2.5;

        @Comment("Default: 0.05")
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
        @Comment("Default: 2.5")
        public double explosionRadius = 2.5;
        @Comment("Default: 0.5")
        public double igniteRadiusMultiplier = 0.5;
    }

    public static class BabyEndermanConfig extends BaseBabyMobConfig {
        @Comment("Default: 0.35")
        public double speedMultiplier = 0.35;

        @Comment("Default: false")
        public boolean canPickUpBlocks = false;
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
