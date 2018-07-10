package com.github.timpeeters.boot.logger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@ConditionalOnProperty(prefix = "contextual.logging", name = "enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(ContextualLoggingProperties.class)
public class ContextualLoggingAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ContextualLoggingInitializer contextualLoggingInitializer() {
        return new ContextualLoggingInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public FilterRegistrationBean contextualLoggingFilterRegistrationBean(Filter contextualLoggingFilter) {
        FilterRegistrationBean filter = new FilterRegistrationBean(contextualLoggingFilter);
        filter.addUrlPatterns("/*");

        return filter;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public Filter contextualLoggingFilter(ContextualLoggingProperties props) {
        return new DefaultContextualLoggingServletFilter(props);
    }
}
