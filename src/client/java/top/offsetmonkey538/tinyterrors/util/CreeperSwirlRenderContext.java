package top.offsetmonkey538.tinyterrors.util;

import net.minecraft.client.render.entity.model.EntityModel;

import java.security.Provider;
import java.util.function.Supplier;

public final class CreeperSwirlRenderContext {
    private CreeperSwirlRenderContext() {

    }

    public static final ThreadLocal<Boolean> isBaby = ThreadLocal.withInitial(() -> false);

    public static <T extends EntityModel<?>> T contextualize(Supplier<T> getModelCall, Boolean isBaby) {
        CreeperSwirlRenderContext.isBaby.set(isBaby);
        final T result = getModelCall.get();
        CreeperSwirlRenderContext.isBaby.remove();

        return result;
    }
}
