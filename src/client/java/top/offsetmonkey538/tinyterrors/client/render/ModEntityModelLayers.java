package top.offsetmonkey538.tinyterrors.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.*;

import java.util.Set;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.id;

public final class ModEntityModelLayers {

    //public static final EntityModelLayer CREEPER_BABY = register("creeper_baby", () -> CreeperEntityModel.getTexturedModelData(Dilation.NONE).transform(new BabyModelTransformer(Set.of("head"))));
    //public static final EntityModelLayer CREEPER_BABY = register("creeper_baby", () -> CreeperEntityModel.getTexturedModelData(Dilation.NONE).transform(ZombieEntityModel.BABY_TRANSFORMER));
    public static final EntityModelLayer CREEPER_BABY = register("creeper_baby", () -> CreeperEntityModel.getTexturedModelData(Dilation.NONE).transform(modelData -> {
        //return ZombieEntityModel.BABY_TRANSFORMER.apply(modelData);
        /// boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, float bodyYOffset, Set<String> headParts
        return new BabyModelTransformer(true, 14.0F, 0.0F, 2.0F, 2.0F, 24.0F, Set.of("head")).apply(modelData);
    }));

    private static EntityModelLayer register(final String id, final EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        final EntityModelLayer layer = new EntityModelLayer(id(id), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, provider);
        return layer;
    }

    public static void register() {
        // Registers stuff by loading the class
    }
}
