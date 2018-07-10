package com.github.timpeeters.boot.logger;

import org.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshedListener extends AbstractEventListener<ContextRefreshedEvent> {
    private final ContextualLoggingLogbackFilter filter;

    public ContextRefreshedListener(ContextualLoggingLogbackFilter filter) {
        this.filter = filter;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        getLoggerContext().addTurboFilter(filter);
    }
}
