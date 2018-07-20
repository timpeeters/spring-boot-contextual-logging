package com.github.timpeeters.boot.logger;

import org.junit.Test;
import org.springframework.boot.logging.LogLevel;

import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesLogLevelSourceTest {
    @Test
    public void empty() {
        assertThat(new PropertiesLogLevelSource(new ContextualLoggingProperties()).getLogLevels(null)).isEmpty();
    }

    @Test
    public void rootLogger() {
        ContextualLoggingProperties props = props(Stream.of(
                new SimpleEntry<>("root", "warn")));

        assertThat(new PropertiesLogLevelSource(props).getLogLevels(null)).containsExactly(
                new SimpleEntry<>("root", LogLevel.WARN));
    }

    @Test
    public void multipleLoggers() {
        ContextualLoggingProperties props = props(Stream.of(
                new SimpleEntry<>("com", "warn"),
                new SimpleEntry<>("com.github", "info")));

        assertThat(new PropertiesLogLevelSource(props).getLogLevels(null)).contains(
                new SimpleEntry<>("com", LogLevel.WARN),
                new SimpleEntry<>("com.github", LogLevel.INFO));
    }

    @Test
    public void invalidLevel() {
        ContextualLoggingProperties props = props(Stream.of(new SimpleEntry<>("org", "invalid-log-level")));

        assertThat(new PropertiesLogLevelSource(props).getLogLevels(null)).contains(
                new SimpleEntry<>("org", LogLevel.OFF));
    }

    private ContextualLoggingProperties props(Stream<SimpleEntry<String, String>> level) {
        ContextualLoggingProperties props = new ContextualLoggingProperties();
        props.setLevel(level.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));

        return props;
    }
}
