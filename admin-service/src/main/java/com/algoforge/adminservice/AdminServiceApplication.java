package com.algoforge.adminservice;

import com.algoforge.adminservice.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(
        basePackages = {
                "com.algoforge.common.feign"
        },
        defaultConfiguration = FeignConfig.class
)
@ComponentScan(basePackages = {"com.algoforge.adminservice", "com.algoforge.common"})
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

}
