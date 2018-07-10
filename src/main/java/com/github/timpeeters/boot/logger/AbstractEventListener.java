package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public abstract class AbstractEventListener<T extends ApplicationEvent> implements ApplicationListener<T> {
    protected LoggerContext getLoggerContext() {
        return ((Logger) LoggerFactory.getLogger("")).getLoggerContext();
    }
}
