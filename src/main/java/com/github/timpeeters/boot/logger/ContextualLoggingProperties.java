package com.github.timpeeters.boot.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

import javax.annotation.PostConstruct;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix = "contextual.logging")
public class ContextualLoggingProperties {
    private static final Logger LOG = LoggerFactory.getLogger(ContextualLoggingProperties.class);

    private boolean enabled;

    /**
     * Log levels severity mapping. Use 'root' for the root logger.
     */
    private Map<String, String> level = new HashMap<>();

    @PostConstruct
    public void logConfiguration() {
        LOG.info(toString());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, String> getLevel() {
        return level;
    }

    public void setLevel(Map<String, String> level) {
        this.level = level;
    }

    public ContextualLoggingContext createContext() {
        return ContextualLoggingContext.create(level.entrySet().stream()
                .map(e -> new SimpleEntry<>(e.getKey(), toLogLevel(e.getValue())))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }

    private LogLevel toLogLevel(String value) {
        try {
            return LogLevel.valueOf(value.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            return LogLevel.OFF;
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("enabled=" + isEnabled())
                .add("level=" + getLevel())
                .toString();
    }
}
