package com.algoforge.common.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import com.algoforge.common.component.JwtUtil;

@Configuration
public class FeignConfig {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Bean
    public RequestInterceptor bearerAuthRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Получаем текущий Authentication
                var authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    UserPrincipal principal = (UserPrincipal)authentication.getPrincipal(); 
                    String token = jwtTokenUtil.generateToken(principal.getUsername(), principal.getAuthorities().stream().map(el -> el.getAuthority()).toList());
                    // Добавляем токен в заголовок Authorization
                    template.header("Authorization", "Bearer " + token);
                    template.header("X-User-Id", String.valueOf(principal.getId()));
                    template.header("X-User-Username", principal.getUsername());
                    template.header("X-User-Email", principal.getEmail());
                    template.header("X-User-Blocked", String.valueOf(principal.isBlocked()));
                }
            }
        };
    }
}
