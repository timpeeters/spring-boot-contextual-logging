Spring Boot Contextual Logging
==============================

[![Build Status](https://api.travis-ci.org/timpeeters/spring-boot-contextual-logging.svg?branch=master)](https://www.travis-ci.org/timpeeters/spring-boot-contextual-logging)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/da1616cd10144c059cab4fd8e6f3eac2)](https://www.codacy.com/app/timpeeters/spring-boot-contextual-logging?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=timpeeters/spring-boot-contextual-logging&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/da1616cd10144c059cab4fd8e6f3eac2)](https://www.codacy.com/app/timpeeters/spring-boot-contextual-logging?utm_source=github.com&utm_medium=referral&utm_content=timpeeters/spring-boot-contextual-logging&utm_campaign=Badge_Coverage)
[![Known Vulnerabilities](https://snyk.io/test/github/timpeeters/spring-boot-contextual-logging/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/timpeeters/spring-boot-contextual-logging?targetFile=pom.xml)
[![Dependabot Status](https://api.dependabot.com/badges/status?host=github&repo=timpeeters/spring-boot-contextual-logging)](https://dependabot.com)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.timpeeters/spring-boot-contextual-logging.svg)](https://repo1.maven.org/maven2/com/github/timpeeters/spring-boot-contextual-logging/)

This project allows to configure Spring Boot logging on a per-request basis. 
Currently this project only supports Logback, the default logging framework in Spring Boot.


Installation
------------

1. Add the following Maven dependency.

```xml
<dependency>
    <groupId>com.github.timpeeters</groupId>
    <artifactId>spring-boot-contextual-logging</artifactId>
    <version>X.X.X</version>
</dependency>
```

Implementation details
----------------------

### ContextualLoggingLogbackFilter

The implementation relies on the use of a Logback `TurboFilter`.

- https://logback.qos.ch/manual/filters.html#TurboFilter 

`TurboFilters` are called every time a logging request is issued.
They are called before the `LoggingEvent` is created.
As such, `TurboFilters` are intended for high performance filtering of logging events.

This projects `ContextualLoggingLogbackFilter` extends the Logback `TurboFilter` abstract class and checks for the presence of a `ContextualLoggingContext` in the `ContextualLoggingContextHolder`.
If a `ContextualLoggingContext` is present, it will use the state in the context to decide whether a log statement should be filtered or not.
If no `ContextualLoggingContext` is present, the filter will return a `FilterReply.NEUTRAL` resulting in default Logback behavior.


### RequestEvaluator

The `RequestEvaluator` interface is responsible for deciding whether contextual logging should be enabled for a given request or not.

The interface takes only one input parameter `HttpServletRequest` and returns a boolean indicating whether contextual logging should be enabled or not.

```java
boolean shouldEnable(HttpServletRequest request);
```

By default a `HeaderRequestEvaluator` is created that looks for the presence of a request header with the name `Contextual-Logging`.
If the header is present, contextual logging is enabled.

```java
@Bean
@ConditionalOnMissingBean
@ConditionalOnWebApplication
public RequestEvaluator requestEvaluator() {
    return new HeaderRequestEvaluator();
}
```

It is possible to register you own `RequestEvaluator` using different logic.
E.g. evaluating the presence of a request parameter.

```java
@Bean
public RequestEvaluator requestEvaluator() {
    return request -> request.getParameter("debug") != null;
}
```


### LogLevelSource

When contextual logging is enabled for a request, the `LogLevelSource` interface will determine what log levels should be applied.

The interface takes only one input parameter `HttpServletRequest` and returns a map with the logger name as key and the log level as value.

```java
Map<String, LogLevel> getLogLevels(HttpServletRequest request);
```

By default two implementations of the interface are shipped: `HeaderLogLevelSource` and `PropertiesLogLevelSource`.

#### HeaderLogLevelSource

Retrieves the log levels from the HTTP headers of the request. 
This implementation is enabled by default.

Configuration:

```properties
contextual.logging.enabled=true
contextual.logging.log-level-source=header
```

E.g. the following headers will result in info level for all loggers and debug level for the com.github logger.

```
GET / HTTP/1.1
Host: localhost
ContextualLogging: root=info
ContextualLogging: com.github=debug
```


#### PropertiesLogLevelSource

Retrieves the log levels from the Spring Environment.

Enabled using the following configuration:

```properties
contextual.logging.enabled=true
contextual.logging.log-level-source=properties
contextual.logging.level.root=info
contextual.logging.level.com.github=debug
```


References
----------

- https://logback.qos.ch/
