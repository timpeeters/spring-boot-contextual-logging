package com.github.timpeeters.boot.logger;

import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestPropertySource(properties = "contextual.logging.enabled=false")
public class ContextualLoggingDisabledIT extends AbstractIT {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextualLoggingDisabled() {
        assertThatThrownBy(() -> applicationContext.getBean(ContextRefreshedListener.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }
}
