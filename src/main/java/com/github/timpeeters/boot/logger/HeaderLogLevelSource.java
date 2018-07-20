package com.github.timpeeters.boot.logger;

import org.springframework.boot.logging.LogLevel;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link LogLevelSource} interface that retrieves log levels from an HTTP header.
 */
public class HeaderLogLevelSource implements LogLevelSource {
    /**
     * Default name of the HTTP header containing the contextual log levels.
     */
    public static final String DEFAULT_HEADER_NAME = "Contextual-Logging";

    private final String headerName;

    public HeaderLogLevelSource() {
        this(DEFAULT_HEADER_NAME);
    }

    public HeaderLogLevelSource(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public Map<String, LogLevel> getLogLevels(HttpServletRequest request) {
        return Collections.list(request.getHeaders(headerName)).stream()
                .filter(this::isValid)
                .map(this::toEntry)
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    private boolean isValid(String value) {
        return value.contains("=");
    }

    private SimpleEntry<String, LogLevel> toEntry(String value) {
        String[] values = value.split("=");

        return new SimpleEntry<>(values[0].trim(), LogLevel.valueOf(values[1].trim().toUpperCase(Locale.ENGLISH)));
    }
}
