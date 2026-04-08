package com.bank.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 允許所有請求，包含 Swagger 與 API
            )
            .csrf(csrf -> csrf.disable())    // 開發時通常也會禁用 CSRF 方便測試 POST
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // 若有使用 H2 Console
        
        return http.build();
    }
}
