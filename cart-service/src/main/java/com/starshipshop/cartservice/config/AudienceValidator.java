package com.starshipshop.cartservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
//    OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        return OAuth2TokenValidatorResult.success();
//        if (jwt.getAudience().contains("messaging")) {
//            return OAuth2TokenValidatorResult.success();
//        } else {
//            return OAuth2TokenValidatorResult.failure(error);
//        }
    }
}