package com.github.timpeeters.boot.logger;

import org.junit.Test;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerNameComparatorTest {
    private final LoggerNameComparator comparator = new LoggerNameComparator(Logger.ROOT_LOGGER_NAME);

    @Test
    public void comparator() {
        assertThat(sort(Arrays.asList("org", Logger.ROOT_LOGGER_NAME, "com", "com.github.timpeeters"), comparator))
                .containsExactly("org", "com.github.timpeeters", "com", Logger.ROOT_LOGGER_NAME);
    }

    @Test
    public void comparator_rootLoggerLowercase() {
        assertThat(sort(Arrays.asList(Logger.ROOT_LOGGER_NAME.toLowerCase(Locale.ENGLISH), "be"), comparator))
                .containsExactly("be", Logger.ROOT_LOGGER_NAME.toLowerCase(Locale.ENGLISH));
    }

    private List<String> sort(List<String> values, Comparator<String> comparator) {
        values.sort(comparator);

        return values;
    }
}
