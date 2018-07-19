package com.github.timpeeters.boot.logger;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = {
        "contextual.logging.enabled=true",
        "contextual.logging.level.com.github.timpeeters=info",
        "contextual.logging.level.com.github.timpeeters.boot.logger=debug",
        "logging.level.com.github.timpeeters.boot.logger=info",
        "logging.level.root=warn"
})
public class ContextualLoggingEnabledIT extends AbstractIT {
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
        HttpHeaders headers = new HttpHeaders();
        headers.add(DefaultContextualLoggingServletFilter.DEBUG_HEADER, "");

        restTemplate.exchange("/", HttpMethod.GET, new HttpEntity<>(headers), String.class);

        assertThat(outputCapture.toString())
                .contains("warn in com.github.timpeeters")
                .contains("info in com.github.timpeeters")
                .contains("debug in com.github.timpeeters.boot.logger");
    }
}
