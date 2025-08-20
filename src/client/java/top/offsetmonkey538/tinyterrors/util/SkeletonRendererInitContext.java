package top.offsetmonkey538.tinyterrors.util;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class SkeletonRendererInitContext {
    private SkeletonRendererInitContext() {

    }

    public static final ThreadLocal<EntityModel<?>> babyModel = new ThreadLocal<>();

    public static void contextualize(Runnable runnable, @NotNull EntityModel<?> babyModel) {
        SkeletonRendererInitContext.babyModel.set(babyModel);
        runnable.run();
        SkeletonRendererInitContext.babyModel.remove();
    }
}
