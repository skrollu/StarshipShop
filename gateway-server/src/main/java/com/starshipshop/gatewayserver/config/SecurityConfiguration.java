package com.starshipshop.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/products/**").permitAll()
                        .pathMatchers("/api/v1/starships/**").permitAll()
                        .pathMatchers("/api/v1/inventory/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2Login();
        return http.build();
    }
}
