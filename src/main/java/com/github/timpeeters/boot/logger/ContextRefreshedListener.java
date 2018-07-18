package com.github.timpeeters.boot.logger;

import org.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshedListener extends AbstractEventListener<ContextRefreshedEvent> {
    private final ContextualLoggingLogbackFilter filter;

    public ContextRefreshedListener(ContextualLoggingLogbackFilter filter) {
        this.filter = filter;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!getLoggerContext().getTurboFilterList().contains(filter)) {
            getLoggerContext().addTurboFilter(filter);
        }
    }
}
