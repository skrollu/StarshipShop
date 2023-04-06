package com.starshipshop.starshipservice.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//   @Bean
//   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//      return http
//              .csrf().disable()
//              .userDetailsService(userDetailsService)
//              .headers(headers -> headers.frameOptions().sameOrigin())
//              .httpBasic(withDefaults())
//              .build();
//   }
    /**
     * For the backend-resources, I indicate that all the endpoints are protected.
     * To request any endpoint, the OAuth2 protocol is necessary, using the server configured and with the given scope.
     * Thus, a JWT will be used to communicate between the backend-resources and backend-auth when backend-resources
     * needs to validate the authentication of a request.
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.mvcMatcher("/**")
//                .authorizeRequests()
////                .mvcMatchers("/**")
////                .access("hasAuthority('SCOPE_message.read')")
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
//        return http.build();
//    }
}