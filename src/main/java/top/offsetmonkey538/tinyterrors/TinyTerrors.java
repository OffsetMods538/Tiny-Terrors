package top.offsetmonkey538.tinyterrors;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.offsetmonkey538.tinyterrors.config.ModConfig;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;

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
}
