package com.algoforge.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.algoforge.apigateway.component.JwtWithAuthFilter;

/**
 * Основная конфигурация Security в API Gateway.
 * Здесь же сконфигурируем CORS, чтобы не было
 * дублирующегося заголовка Access-Control-Allow-Origin.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtWithAuthFilter jwtRequestFilter;

    /**
     * Настройка Spring Security + включение cors().
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Включаем обработку CORS на уровне Spring Security
                .cors().and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/tasks/**").authenticated()
                        .anyRequest().permitAll()
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

    /**
     * Глобальная конфигурация CORS в рамках WebMvcConfigurer:
     * Разрешаем фронту http://localhost:4200 делать запросы.
     * AllowedHeaders = "*" означает, что позволяем любые кастомные заголовки.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        // Открываем CORS на любые пути, можно ограничиться "/api/**"
                        .addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}


//package com.algoforge.apigateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.algoforge.apigateway.component.JwtWithAuthFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private JwtWithAuthFilter jwtRequestFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                                    .requestMatchers("/api/tasks/**").authenticated()
//                                    .anyRequest().permitAll())
//            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authExp) -> response.sendError(401, "User is not unthenicated")))
//            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return http.build();
//    }
//
//}
