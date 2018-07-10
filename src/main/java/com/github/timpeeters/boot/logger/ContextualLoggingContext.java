package com.github.timpeeters.boot.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.springframework.boot.logging.LogLevel;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ContextualLoggingContext {
    private static final EnumMap<LogLevel, Level> LEVELS = new EnumMap<>(LogLevel.class);

    private final Map<String, LogLevel> levels;

    static {
        LEVELS.put(LogLevel.TRACE, Level.TRACE);
        LEVELS.put(LogLevel.DEBUG, Level.DEBUG);
        LEVELS.put(LogLevel.INFO, Level.INFO);
        LEVELS.put(LogLevel.WARN, Level.WARN);
        LEVELS.put(LogLevel.ERROR, Level.ERROR);
        LEVELS.put(LogLevel.FATAL, Level.ERROR);
        LEVELS.put(LogLevel.OFF, Level.OFF);
    }

    private ContextualLoggingContext(Map<String, LogLevel> levels) {
        SortedMap<String, LogLevel> sortedMap = new TreeMap<>(new LoggerNameComparator(Logger.ROOT_LOGGER_NAME));
        sortedMap.putAll(levels);

        this.levels = Collections.unmodifiableSortedMap(sortedMap);
    }

    public static ContextualLoggingContext create(Map<String, LogLevel> levels) {
        return new ContextualLoggingContext(levels);
    }

    public boolean shouldLog(Logger logger, Level level) {
        return levels.entrySet().stream()
                .filter(isMatchingLoggerName(logger)).findFirst()
                .map(isLevelGreaterOrEqual(level)).orElse(false);
    }

    private Predicate<Map.Entry<String, LogLevel>> isMatchingLoggerName(Logger logger) {
        return e -> logger.getName().startsWith(e.getKey()) || e.getKey().equalsIgnoreCase(Logger.ROOT_LOGGER_NAME);
    }

    private Function<Map.Entry<String, LogLevel>, Boolean> isLevelGreaterOrEqual(Level level) {
        return e -> level.isGreaterOrEqual(LEVELS.get(e.getValue()));
    }
}
