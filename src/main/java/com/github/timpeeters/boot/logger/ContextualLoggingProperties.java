package com.github.timpeeters.boot.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@ConfigurationProperties(prefix = "contextual.logging")
public class ContextualLoggingProperties {
    private static final Logger LOG = LoggerFactory.getLogger(ContextualLoggingProperties.class);

    private boolean enabled;

    /**
     * Source from where to read the log levels, defaults to HTTP headers.
     */
    private Source logLevelSource = Source.HEADER;

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

    public Source getLogLevelSource() {
        return logLevelSource;
    }

    public void setLogLevelSource(Source logLevelSource) {
        this.logLevelSource = logLevelSource;
    }

    public Map<String, String> getLevel() {
        return level;
    }

    public void setLevel(Map<String, String> level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("enabled=" + isEnabled())
                .add("logLevelSource=" + getLogLevelSource())
                .add("level=" + getLevel())
                .toString();
    }

    public enum Source {
        HEADER,
        PROPERTIES
    }
}
