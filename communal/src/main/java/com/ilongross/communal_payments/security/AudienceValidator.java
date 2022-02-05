package com.ilongross.communal_payments.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final String audience;

    public AudienceValidator(String audience) {
        this.audience = audience;
    }


    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if(jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        }
        var invalid_token = new OAuth2Error("invalid_token", "Audience is not equals.", null);
        return OAuth2TokenValidatorResult.failure(invalid_token);
    }
}
