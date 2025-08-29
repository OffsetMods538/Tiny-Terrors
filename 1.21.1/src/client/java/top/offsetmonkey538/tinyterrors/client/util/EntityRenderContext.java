package top.offsetmonkey538.tinyterrors.client.util;

public final class EntityRenderContext {
    private EntityRenderContext() {

    }

    public static final ThreadLocal<Boolean> isBaby = ThreadLocal.withInitial(() -> false);

    public static void contextualize(Runnable renderCall, boolean isBaby) {
        EntityRenderContext.isBaby.set(isBaby);
        renderCall.run();
        EntityRenderContext.isBaby.remove();
    }
}
