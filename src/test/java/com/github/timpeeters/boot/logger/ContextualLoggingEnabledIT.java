package com.github.timpeeters.boot.logger;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = {
        "contextual.logging.enabled=true",
        "logging.level.com.github.timpeeters.boot.logger=info"
})
public class ContextualLoggingEnabledIT extends AbstractIT {
    @Rule
    public final OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextualLoggingEnabled() {
        restTemplate.getForEntity("/", String.class);

        assertThat(outputCapture.toString())
                .contains("info logging")
                .doesNotContain("debug logging");
    }

    @Test
    public void contextualLoggingEnabled_debug() {
        restTemplate.getForEntity("/?debug", String.class);

        assertThat(outputCapture.toString())
                .contains("debug logging");
    }
}
