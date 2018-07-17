package com.github.timpeeters.boot.logger;

import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextualLoggingContextHolderTest {
    @Test
    public void clear() {
        ContextualLoggingContextHolder.set(ContextualLoggingContext.create(Collections.emptyMap()));
        ContextualLoggingContextHolder.clear();

        assertThat(ContextualLoggingContextHolder.get()).isNull();
    }
}
