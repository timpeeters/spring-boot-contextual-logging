package com.github.timpeeters.boot.logger;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextualLoggingServletFilter extends OncePerRequestFilter {
    private final RequestEvaluator requestEvaluator;
    private final LogLevelSource logLevelSource;

    public ContextualLoggingServletFilter(RequestEvaluator requestEvaluator, LogLevelSource logLevelSource) {
        this.requestEvaluator = requestEvaluator;
        this.logLevelSource = logLevelSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            enableContextualLoggingIfNecessary(request);

            chain.doFilter(request, response);
        } finally {
            ContextualLoggingContextHolder.clear();
        }
    }

    private void enableContextualLoggingIfNecessary(HttpServletRequest request) {
        if (requestEvaluator.shouldEnable(request)) {
            ContextualLoggingContextHolder.set(ContextualLoggingContext.create(logLevelSource.getLogLevels(request)));
        }
    }
}
