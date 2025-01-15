package com.algoforge.adminservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AdminFeignConfig {

    @Bean
    public RequestInterceptor authForwardingRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                var auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.getCredentials() != null) {
                    String jwt = auth.getCredentials().toString();
                    template.header("Authorization", "Bearer " + jwt);
                }
            }
        };
    }
}
