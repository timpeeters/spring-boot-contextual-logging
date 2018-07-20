package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextualLoggingContextTest {
    private static final String MY_PACKAGE = "com.github.timpeeters";
    private static final String ANOTHER_PACKAGE = "org.springframework";

    private final ContextualLoggingContext context = ContextualLoggingContext.create(Stream.of(
            new SimpleEntry<>(Logger.ROOT_LOGGER_NAME, LogLevel.ERROR),
            new SimpleEntry<>(MY_PACKAGE, LogLevel.INFO))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));

    @Test
    public void shouldLog() {
        assertThat(context.shouldLog(logger(MY_PACKAGE), Level.INFO)).isTrue();
        assertThat(context.shouldLog(logger(MY_PACKAGE), Level.DEBUG)).isFalse();
    }

    @Test
    public void shouldLog_rootLogger() {
        assertThat(context.shouldLog(logger(ANOTHER_PACKAGE), Level.ERROR)).isTrue();
        assertThat(context.shouldLog(logger(ANOTHER_PACKAGE), Level.INFO)).isFalse();
    }

    @Test
    public void getLogLevels() {
        assertThat(context.getLogLevels()).containsExactlyInAnyOrder("com.github.timpeeters=INFO", "ROOT=ERROR");
    }

    private Logger logger(String name) {
        return (Logger) LoggerFactory.getLogger(name);
    }
}
