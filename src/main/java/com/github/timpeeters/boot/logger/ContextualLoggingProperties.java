package com.github.timpeeters.boot.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@ConfigurationProperties(ignoreInvalidFields = true, prefix = "contextual.logging")
public class ContextualLoggingProperties {
    private static final Logger LOG = LoggerFactory.getLogger(ContextualLoggingProperties.class);

    private boolean enabled;
    private final Map<String, String> level = new HashMap<>();

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

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("enabled=" + isEnabled())
                .add("level=" + getLevel())
                .toString();
    }
}
