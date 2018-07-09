package com.github.timpeeters.boot.logger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "contextual.logging", name = "enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(ContextualLoggingProperties.class)
public class ContextualLoggingAutoConfiguration {
    @Bean
    public ContextualLoggingInitializer contextualLoggingInitializer() {
        return new ContextualLoggingInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public FilterRegistrationBean contextualLoggingFilter(ContextualLoggingProperties props) {
        FilterRegistrationBean filter = new FilterRegistrationBean(new DefaultContextualLoggingServletFilter(props));
        filter.addUrlPatterns("/*");

        return filter;
    }
}
