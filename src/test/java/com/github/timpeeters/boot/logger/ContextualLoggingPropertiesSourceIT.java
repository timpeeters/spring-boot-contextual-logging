package com.github.timpeeters.boot.logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        "contextual.logging.enabled=true",
        "contextual.logging.log-level-source=properties",
        "contextual.logging.level.com.github.timpeeters=info",
        "contextual.logging.level.com.github.timpeeters.boot.logger=debug",
        "logging.level.com.github.timpeeters.boot.logger=info",
        "logging.level.root=warn"
})
public class ContextualLoggingPropertiesSourceIT extends AbstractLoggingEnabledIT {
    @Override
    protected HttpEntity<Object> createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderRequestEvaluator.DEFAULT_HEADER_NAME, "");

        return new HttpEntity<>(headers);
    }
}
