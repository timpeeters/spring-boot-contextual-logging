package com.github.timpeeters.boot.logger;

import javax.servlet.http.HttpServletRequest;

public class DefaultContextualLoggingServletFilter extends AbstractContextualLoggingServletFilter {
    public static final String DEBUG_HEADER = "X-Debug";

    public DefaultContextualLoggingServletFilter(ContextualLoggingProperties props) {
        super(props);
    }

    @Override
    protected boolean shouldEnableContextualLogging(HttpServletRequest req) {
        return req.getHeader(DEBUG_HEADER) != null;
    }
}
