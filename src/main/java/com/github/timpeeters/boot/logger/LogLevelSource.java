package com.github.timpeeters.boot.logger;

import org.springframework.boot.logging.LogLevel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Source from which to retrieve to contextual log levels.
 *
 * @author Tim Peeters
 */
public interface LogLevelSource {
    /**
     * Returns the contextual log levels for the given request.
     *
     * @param request the request to evaluate
     * @return the contextual log levels for the given request
     */
    Map<String, LogLevel> getLogLevels(HttpServletRequest request);
}
