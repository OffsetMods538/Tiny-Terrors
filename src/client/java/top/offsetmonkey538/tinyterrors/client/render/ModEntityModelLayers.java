package top.offsetmonkey538.tinyterrors.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.*;

import java.util.Set;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.id;

public final class ModEntityModelLayers {

    public static final EntityModelLayer CREEPER_BABY = register("creeper_baby", () -> CreeperEntityModel.getTexturedModelData(Dilation.NONE), 14);

    private static EntityModelLayer register(final String id, final EntityModelLayerRegistry.TexturedModelDataProvider baseProvider, final float headOffset) {
        final EntityModelLayer layer = new EntityModelLayer(id(id), "main");
        final BabyModelTransformer modelTransformer = new BabyModelTransformer(true, headOffset, 0.0F, 2.0F, 2.0F, 24.0F, Set.of("head"));

        EntityModelLayerRegistry.registerModelLayer(layer, () -> baseProvider.createModelData().transform(modelTransformer));

        return layer;
    }

    public static void register() {
        // Registers stuff by loading the class
    }
}
