package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Level;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextualLoggingServletFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        try {
            if (req.getParameter("debug") != null) {
                ContextualLoggingContextHolder.set(new ContextualLoggingContext(Level.DEBUG));
            }

            chain.doFilter(req, resp);
        } finally {
            ContextualLoggingContextHolder.clear();
        }
    }
}
