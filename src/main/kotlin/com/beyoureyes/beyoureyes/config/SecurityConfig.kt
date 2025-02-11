package com.beyoureyes.beyoureyes.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http
            .authorizeHttpRequests { auth -> auth
                    .requestMatchers("/", "/user/login", "/user/verify-token").permitAll()
                .anyRequest().authenticated()
            }
            .csrf { it.disable() }
            .cors { }

        return http.build()
    }
}