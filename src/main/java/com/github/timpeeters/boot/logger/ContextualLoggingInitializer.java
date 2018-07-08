package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class ContextualLoggingInitializer {
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        LoggerContext loggerContext = ((Logger) LoggerFactory.getLogger("")).getLoggerContext();
        loggerContext.addTurboFilter(new ContextualLoggingLogbackFilter());
    }
}
