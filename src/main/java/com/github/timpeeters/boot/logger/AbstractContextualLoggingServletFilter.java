package com.github.timpeeters.boot.logger;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractContextualLoggingServletFilter extends OncePerRequestFilter {
    protected final ContextualLoggingProperties props;

    protected AbstractContextualLoggingServletFilter(ContextualLoggingProperties props) {
        this.props = props;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        try {
            if (shouldEnableContextualLogging(req)) {
                ContextualLoggingContextHolder.set(props.createContext());
            }

            chain.doFilter(req, resp);
        } finally {
            ContextualLoggingContextHolder.clear();
        }
    }

    protected abstract boolean shouldEnableContextualLogging(HttpServletRequest req);
}
