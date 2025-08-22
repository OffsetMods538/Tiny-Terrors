package top.offsetmonkey538.tinyterrors.common;

import net.fabricmc.api.ClientModInitializer;
import top.offsetmonkey538.tinyterrors.common.client.render.ModEntityModelLayers;

public class TinyTerrorsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
        ModEntityModelLayers.register();
	}
}
