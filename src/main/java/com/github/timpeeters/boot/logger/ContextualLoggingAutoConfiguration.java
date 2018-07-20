package com.github.timpeeters.boot.logger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@ConditionalOnProperty(prefix = "contextual.logging", name = "enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(ContextualLoggingProperties.class)
public class ContextualLoggingAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ContextualLoggingLogbackFilter contextualLoggingLogbackFilter() {
        return new ContextualLoggingLogbackFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ContextRefreshedListener contextRefreshedListener(ContextualLoggingLogbackFilter filter) {
        return new ContextRefreshedListener(filter);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(RefreshScopeRefreshedEvent.class)
    public RefreshScopeRefreshedListener refreshScopeRefreshedListener(ContextualLoggingLogbackFilter filter) {
        return new RefreshScopeRefreshedListener(filter);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public ContextualLoggingServletFilter contextualLoggingServletFilter(
            RequestEvaluator requestEvaluator, LogLevelSource logLevelSource) {
        return new ContextualLoggingServletFilter(requestEvaluator, logLevelSource);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public FilterRegistrationBean contextualLoggingFilterRegistrationBean(ContextualLoggingServletFilter f) {
        FilterRegistrationBean filter = new FilterRegistrationBean(f);
        filter.addUrlPatterns("/*");
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return filter;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "contextual.logging", name = "log-level-source", havingValue = "header")
    @ConditionalOnWebApplication
    public HeaderLogLevelSource headerLogLevelSource() {
        return new HeaderLogLevelSource();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "contextual.logging", name = "log-level-source", havingValue = "properties")
    @ConditionalOnWebApplication
    public PropertiesLogLevelSource propertiesLogLevelSource(ContextualLoggingProperties props) {
        return new PropertiesLogLevelSource(props);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public RequestEvaluator requestEvaluator() {
        return new HeaderRequestEvaluator();
    }
}
