package com.algoforge.testrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.algoforge.testrunner", "com.algoforge.common"})
public class TestRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestRunnerApplication.class, args);
    }

}
