package com.github.timpeeters.boot.logger;

import javax.servlet.http.HttpServletRequest;

public class DefaultContextualLoggingServletFilter extends AbstractContextualLoggingServletFilter {
    public DefaultContextualLoggingServletFilter(ContextualLoggingProperties props) {
        super(props);
    }

    @Override
    protected boolean shouldEnableContextualLogging(HttpServletRequest req) {
        return req.getParameter("debug") != null;
    }
}
