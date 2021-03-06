package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;

public class ContextualLoggingLogbackFilter extends TurboFilter {
    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        ContextualLoggingContext ctx = ContextualLoggingContextHolder.get();

        if (ctx != null && ctx.shouldLog(logger, level)) {
            return FilterReply.ACCEPT;
        }

        return FilterReply.NEUTRAL;
    }
}
