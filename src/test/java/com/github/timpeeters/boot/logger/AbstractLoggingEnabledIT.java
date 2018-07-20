package com.github.timpeeters.boot.logger;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractLoggingEnabledIT extends AbstractIT {
    @Rule
    public final OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextualLogging() {
        restTemplate.getForEntity("/", String.class);

        assertThat(outputCapture.toString())
                .contains("warn in com.github.timpeeters")
                .doesNotContain("info in com.github.timpeeters");
    }

    @Test
    public void contextualLogging_debug() {
        restTemplate.exchange("/", HttpMethod.GET, createRequestEntity(), String.class);

        assertThat(outputCapture.toString())
                .contains("warn in com.github.timpeeters")
                .contains("info in com.github.timpeeters")
                .contains("debug in com.github.timpeeters.boot.logger");
    }

    protected abstract HttpEntity<Object> createRequestEntity();
}
