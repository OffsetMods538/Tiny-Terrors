package top.offsetmonkey538.tinyterrors;

import net.fabricmc.api.ClientModInitializer;
import top.offsetmonkey538.tinyterrors.client.render.ModEntityModelLayers;

public class TinyTerrorsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
        ModEntityModelLayers.register();
	}
}
