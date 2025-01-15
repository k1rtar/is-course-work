package com.algoforge.contestservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.algoforge.common.auth.JwtRequestFilter;

/**
 * Удалили все упоминания cors().
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/contests/public/**").permitAll()
                        .requestMatchers("/api/contests/**").authenticated()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exHandling ->
                        exHandling.authenticationEntryPoint(
                                (req, resp, authExp) -> resp.sendError(401, "User is not authenticated")
                        )
                )
                .sessionManagement(sessionMgmt ->
                        sessionMgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}