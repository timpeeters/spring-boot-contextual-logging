package com.github.timpeeters.boot.logger;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {
    @SpringBootApplication
    public static class TestApplication {

        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }

        @RestController("/")
        public static class TestController {
            @GetMapping
            public void get() {
                getLogger("com.github.timpeeters").warn("warn in com.github.timpeeters");
                getLogger("com.github.timpeeters").info("info in com.github.timpeeters");
                getLogger("com.github.timpeeters.boot.logger").debug("debug in com.github.timpeeters.boot.logger");
            }
        }

    }
}
