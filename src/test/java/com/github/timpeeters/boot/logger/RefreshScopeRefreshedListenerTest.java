package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;

import static org.assertj.core.api.Assertions.assertThat;

public class RefreshScopeRefreshedListenerTest {
    private final ContextualLoggingLogbackFilter filter = new ContextualLoggingLogbackFilter();
    private final RefreshScopeRefreshedListener listener = new RefreshScopeRefreshedListener(filter);

    @Test
    public void filterNotYetRegistered() {
        listener.onApplicationEvent(new RefreshScopeRefreshedEvent());

        assertThat(getLoggerContext().getTurboFilterList()).contains(filter);
    }

    @Test
    public void filterAlreadyRegistered() {
        getLoggerContext().addTurboFilter(filter);

        listener.onApplicationEvent(new RefreshScopeRefreshedEvent());

        assertThat(getLoggerContext().getTurboFilterList()).containsOnlyOnce(filter);
    }

    private LoggerContext getLoggerContext() {
        return ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).getLoggerContext();
    }
}
