package com.github.timpeeters.boot.logger;

import org.junit.Test;
import org.springframework.boot.logging.LogLevel;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.AbstractMap.SimpleEntry;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderLogLevelSourceTest {
    @Test
    public void defaultHeaderAbsent() {
        assertThat(new HeaderLogLevelSource().getLogLevels(new MockHttpServletRequest())).isEmpty();
    }

    @Test
    public void defaultHeaderPresent() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HeaderLogLevelSource.DEFAULT_HEADER_NAME, "com.github.timpeeters=debug");

        assertThat(new HeaderLogLevelSource().getLogLevels(request)).containsExactly(
                new SimpleEntry<>("com.github.timpeeters", LogLevel.DEBUG));
    }

    @Test
    public void defaultHeaderPresentWithMultipleLoggers() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HeaderLogLevelSource.DEFAULT_HEADER_NAME, "com=warn");
        request.addHeader(HeaderLogLevelSource.DEFAULT_HEADER_NAME, "com.github=info");

        assertThat(new HeaderLogLevelSource().getLogLevels(request)).contains(
                new SimpleEntry<>("com", LogLevel.WARN),
                new SimpleEntry<>("com.github", LogLevel.INFO));
    }

    @Test
    public void defaultHeaderPresentRootLogger() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HeaderLogLevelSource.DEFAULT_HEADER_NAME, "root=warn");

        assertThat(new HeaderLogLevelSource().getLogLevels(request)).containsExactly(
                new SimpleEntry<>("root", LogLevel.WARN));
    }
}
