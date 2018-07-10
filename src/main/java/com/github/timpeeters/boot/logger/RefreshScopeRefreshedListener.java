package com.github.timpeeters.boot.logger;

import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;

public class RefreshScopeRefreshedListener extends AbstractEventListener<RefreshScopeRefreshedEvent> {
    private final ContextualLoggingLogbackFilter filter;

    public RefreshScopeRefreshedListener(ContextualLoggingLogbackFilter filter) {
        this.filter = filter;
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        if (!getLoggerContext().getTurboFilterList().contains(filter)) {
            getLoggerContext().addTurboFilter(filter);
        }
    }
}
