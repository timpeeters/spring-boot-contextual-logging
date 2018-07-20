package com.github.timpeeters.boot.logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        "contextual.logging.enabled=true",
        "contextual.logging.log-level-source=header",
        "logging.level.com.github.timpeeters.boot.logger=info",
        "logging.level.root=warn"
})
public class ContextualLoggingHeaderSourceIT extends AbstractLoggingEnabledIT {
    @Override
    protected HttpEntity<Object> createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderLogLevelSource.DEFAULT_HEADER_NAME, "com.github.timpeeters=info");
        headers.add(HeaderLogLevelSource.DEFAULT_HEADER_NAME, "com.github.timpeeters.boot.logger=debug");

        return new HttpEntity<>(headers);
    }
}
