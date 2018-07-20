package com.github.timpeeters.boot.logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Evaluates whether contextual logging should be enabled for the given request or not.
 *
 * @author Tim Peeters
 */
@FunctionalInterface
public interface RequestEvaluator {
    /**
     * Returns true if contextual logging should be enabled for the given request, false otherwise.
     *
     * @param request the request to evaluate
     * @return true if contextual logging should be enabled, false otherwise
     */
    boolean shouldEnable(HttpServletRequest request);
}
