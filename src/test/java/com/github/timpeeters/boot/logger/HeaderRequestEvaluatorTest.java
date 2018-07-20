package com.github.timpeeters.boot.logger;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderRequestEvaluatorTest {
    @Test
    public void defaultHeaderAbsent() {
        assertThat(new HeaderRequestEvaluator().shouldEnable(new MockHttpServletRequest())).isFalse();
    }

    @Test
    public void defaultHeaderPresent() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HeaderRequestEvaluator.DEFAULT_HEADER_NAME, "");

        assertThat(new HeaderRequestEvaluator().shouldEnable(request)).isTrue();
    }

    @Test
    public void customHeaderPresent() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Custom-Header", "");

        assertThat(new HeaderRequestEvaluator("Custom-Header").shouldEnable(request)).isTrue();
    }
}
