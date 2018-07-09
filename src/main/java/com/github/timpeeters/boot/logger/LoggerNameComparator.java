package com.github.timpeeters.boot.logger;

import java.util.Comparator;

public class LoggerNameComparator implements Comparator<String> {
    private final String rootLoggerName;

    public LoggerNameComparator(String rootLoggerName) {
        this.rootLoggerName = rootLoggerName;
    }

    @Override
    public int compare(String loggerName, String anotherLoggerName) {
        if (rootLoggerName.equals(loggerName)) {
            return 1;
        }

        return anotherLoggerName.compareTo(loggerName);
    }
}
