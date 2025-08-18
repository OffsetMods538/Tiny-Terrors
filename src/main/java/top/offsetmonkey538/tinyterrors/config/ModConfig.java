package top.offsetmonkey538.tinyterrors.config;

import blue.endless.jankson.Comment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.offsetconfig538.api.config.Config;

import java.nio.file.Path;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.MOD_ID;

public class ModConfig implements Config {

    public BabyMobConfig creeperConfig = new BabyCreeperConfig();

    public static class BabyMobConfig {
        @Comment("Default: 0.5")
        public double speedMultiplier = 0.5;

        @Comment("Default: 0.5")
        public double xpMultiplier = 2.5;

        @Comment("Default: 0.05")
        public float spawnChance = 0.05f;

        @Comment("Default: 0.05")
        public float spawnJockeyChance = 0.05f;

        public boolean shouldBeBaby(final Random random) {
            return random.nextFloat() < spawnChance;
        }

        public boolean shouldBeJockey(final Random random) {
            return random.nextFloat() < spawnJockeyChance;
        }
    }

    public static class BabyCreeperConfig extends BabyMobConfig {

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
