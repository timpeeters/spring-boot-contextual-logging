package com.github.timpeeters.boot.logger;

public final class ContextualLoggingContextHolder {
    private static final InheritableThreadLocal<ContextualLoggingContext> CONTEXT = new InheritableThreadLocal<>();

    private ContextualLoggingContextHolder() {
    }

    public static ContextualLoggingContext get() {
        return CONTEXT.get();
    }

    public static void set(ContextualLoggingContext ctx) {
        CONTEXT.set(ctx);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
