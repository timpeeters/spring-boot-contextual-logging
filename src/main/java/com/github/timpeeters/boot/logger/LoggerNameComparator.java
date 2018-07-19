package com.github.timpeeters.boot.logger;

import java.util.Comparator;
import java.util.function.Function;

public class LoggerNameComparator implements Comparator<String> {
    private final String rootLoggerName;

    public LoggerNameComparator(String rootLoggerName) {
        this.rootLoggerName = rootLoggerName;
    }

    @Override
    public int compare(String loggerName, String anotherLoggerName) {
        return Comparator.comparing(rootLoggerLast())
                .thenComparing(Comparator.reverseOrder())
                .compare(loggerName, anotherLoggerName);
    }

    private Function<String, Integer> rootLoggerLast() {
        return v -> v.equalsIgnoreCase(rootLoggerName) ? 1 : 0;
    }
}
