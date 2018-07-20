package com.github.timpeeters.boot.logger;

import org.springframework.boot.logging.LogLevel;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap.SimpleEntry;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link LogLevelSource} interface that retrieves log levels from
 * {@link ContextualLoggingProperties}.
 */
public class PropertiesLogLevelSource implements LogLevelSource {
    private final ContextualLoggingProperties props;

    public PropertiesLogLevelSource(ContextualLoggingProperties props) {
        this.props = props;
    }

    @Override
    public Map<String, LogLevel> getLogLevels(HttpServletRequest request) {
        return props.getLevel().entrySet().stream()
                .map(this::toEntry)
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    private SimpleEntry<String, LogLevel> toEntry(Entry<String, String> entry) {
        return new SimpleEntry<>(entry.getKey().trim(), toLogLevel(entry.getValue()));
    }

    private LogLevel toLogLevel(String value) {
        try {
            return LogLevel.valueOf(value.trim().toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            return LogLevel.OFF;
        }
    }
}
