package com.github.timpeeters.boot.logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Default implementation of the {@link RequestEvaluator} that decides whether contextual logging
 * should be enabled based on the presence of an HTTP header.
 */
public class HeaderRequestEvaluator implements RequestEvaluator {
    /**
     * Default name of the HTTP header that indicates whether contextual logging should be enabled or not.
     */
    public static final String DEFAULT_HEADER_NAME = "Contextual-Logging";

    /**
     * Name of the HTTP header.
     */
    private final String headerName;

    public HeaderRequestEvaluator() {
        this(DEFAULT_HEADER_NAME);
    }

    public HeaderRequestEvaluator(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public boolean shouldEnable(HttpServletRequest request) {
        return request.getHeader(headerName) != null;
    }
}
