package com.algoforge.contestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.algoforge.contestservice",
        "com.algoforge.common"
})
public class ContestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContestServiceApplication.class, args);
    }
}
