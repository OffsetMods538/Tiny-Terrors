package top.offsetmonkey538.tinyterrors.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.*;
import top.offsetmonkey538.tinyterrors.mixin.client.render.entity.model.EntityModelsMixin;

import java.util.Set;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.id;

public final class ModEntityModelLayers {
    private static final TexturedModelData INNER_ARMOR = TexturedModelData.of(ArmorEntityModel.getModelData(EntityModelsMixin.getHAT_DILATION()),   64, 32);
    private static final TexturedModelData OUTER_ARMOR = TexturedModelData.of(ArmorEntityModel.getModelData(EntityModelsMixin.getARMOR_DILATION()), 64, 32);


    public static final EntityModelLayer CREEPER_BABY                        = register("creeper_baby",         "main",        () -> CreeperEntityModel.getTexturedModelData(Dilation.NONE),                                 14.0f);
    public static final EntityModelLayer CREEPER_BABY_ARMOR                  = register("creeper_baby",         "armor",       () -> CreeperEntityModel.getTexturedModelData(new Dilation(2.0F)),                            14.0f);

    public static final EntityModelLayer ENDERMAN_BABY                       = register("enderman_baby",        "main",        EndermanEntityModel::getTexturedModelData,                                                    20.5f);

    public static final EntityModelLayer SKELETON_BABY                       = register("skeleton_baby",        "main",        SkeletonEntityModel::getTexturedModelData,                                                    16.0f);
    public static final EntityModelLayer SKELETON_BABY_INNER_ARMOR           = register("skeleton_baby",        "inner_armor", () -> INNER_ARMOR,                                                                            16.0f);
    public static final EntityModelLayer SKELETON_BABY_OUTER_ARMOR           = register("skeleton_baby",        "outer_armor", () -> OUTER_ARMOR,                                                                            16.0f);

    public static final EntityModelLayer BOGGED_BABY                         = register("bogged_baby",          "main",        BoggedEntityModel::getTexturedModelData,                                                      16.0f);
    public static final EntityModelLayer BOGGED_BABY_INNER_ARMOR             = register("bogged_baby",          "inner_armor", () -> INNER_ARMOR,                                                                            16.0f);
    public static final EntityModelLayer BOGGED_BABY_OUTER_ARMOR             = register("bogged_baby",          "outer_armor", () -> OUTER_ARMOR,                                                                            16.0f);
    public static final EntityModelLayer BOGGED_BABY_OUTER                   = register("bogged_baby",          "outer",       () -> TexturedModelData.of(BipedEntityModel.getModelData(new Dilation(0.2F), 0.0F), 64, 32),  16.0f);

    public static final EntityModelLayer STRAY_BABY                          = register("stray_baby",           "main",        SkeletonEntityModel::getTexturedModelData,                                                    16.0f);
    public static final EntityModelLayer STRAY_BABY_INNER_ARMOR              = register("stray_baby",           "inner_armor", () -> INNER_ARMOR,                                                                            16.0f);
    public static final EntityModelLayer STRAY_BABY_OUTER_ARMOR              = register("stray_baby",           "outer_armor", () -> OUTER_ARMOR,                                                                            16.0f);
    public static final EntityModelLayer STRAY_BABY_OUTER                    = register("stray_baby",           "outer",       () -> TexturedModelData.of(BipedEntityModel.getModelData(new Dilation(0.25F), 0.0F), 64, 32), 16.0f);

    public static final EntityModelLayer WITHER_SKELETON_BABY                = register("wither_skeleton_baby", "main",        SkeletonEntityModel::getTexturedModelData,                                                    16.0f, ModelTransformer.scaling(1.2f));
    public static final EntityModelLayer WITHER_SKELETON_BABY_INNER_ARMOR    = register("wither_skeleton_baby", "inner_armor", () -> INNER_ARMOR,                                                                            16.0f, ModelTransformer.scaling(1.2f));
    public static final EntityModelLayer WITHER_SKELETON_BABY_OUTER_ARMOR    = register("wither_skeleton_baby", "outer_armor", () -> OUTER_ARMOR,                                                                            16.0f, ModelTransformer.scaling(1.2f));


    private static EntityModelLayer register(final String id, final String layerId, final EntityModelLayerRegistry.TexturedModelDataProvider baseProvider, final float headOffset) {
        return register(id, layerId, baseProvider, headOffset, ModelTransformer.NO_OP);
    }
    private static EntityModelLayer register(final String id, final String layerId, final EntityModelLayerRegistry.TexturedModelDataProvider baseProvider, final float headOffset, ModelTransformer transformer) {
        final EntityModelLayer layer = new EntityModelLayer(id(id), layerId);
        final BabyModelTransformer modelTransformer = new BabyModelTransformer(true, headOffset, 0.0F, 2.0F, 2.0F, 24.0F, Set.of("head"));

        EntityModelLayerRegistry.registerModelLayer(layer, () -> baseProvider.createModelData().transform(modelTransformer).transform(transformer));

        return layer;
    }

    public static void register() {
        // Registers stuff by loading the class
    }
}
