package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class ContextualLoggingContext {
    private final Level level;

    public ContextualLoggingContext(Level level) {
        this.level = level;
    }

    public boolean shouldLog(Logger logger, Level level) {
        return level.isGreaterOrEqual(this.level);
    }
}
