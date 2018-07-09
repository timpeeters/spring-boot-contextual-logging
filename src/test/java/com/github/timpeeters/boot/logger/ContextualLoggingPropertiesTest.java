package com.github.timpeeters.boot.logger;

import org.junit.Test;
import org.springframework.boot.logging.LogLevel;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextualLoggingPropertiesTest {

    @Test
    public void ignoreInvalidLevels() {
        ContextualLoggingContext expected = ContextualLoggingContext
                .create(Collections.singletonMap("com.github", LogLevel.OFF));

        ContextualLoggingProperties props = new ContextualLoggingProperties();
        props.setLevel(Collections.singletonMap("com.github", "INVALID_LEVEL"));

        assertThat(props.createContext()).isEqualToComparingFieldByField(expected);
    }
}
