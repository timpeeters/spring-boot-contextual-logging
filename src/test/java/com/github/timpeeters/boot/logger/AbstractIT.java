package com.github.timpeeters.boot.logger;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {
    @SpringBootApplication
    public static class TestApplication {
        private static final Logger LOG = LoggerFactory.getLogger(TestApplication.class);

        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }

        @RestController("/")
        public static class TestController {
            @GetMapping
            public void get() {
                LOG.info("info logging");
                LOG.debug("debug logging");
            }
        }
    }
}
