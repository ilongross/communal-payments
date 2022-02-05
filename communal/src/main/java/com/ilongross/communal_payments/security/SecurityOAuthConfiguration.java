package com.ilongross.communal_payments.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class SecurityOAuthConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${auth.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        var jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuerUri);

        var audienceValidator = (OAuth2TokenValidator<Jwt>) new AudienceValidator(audience);
        var issuerValidator =  JwtValidators.createDefaultWithIssuer(issuerUri);
        var delegatingValidator = new DelegatingOAuth2TokenValidator<>(issuerValidator, audienceValidator);
        jwtDecoder.setJwtValidator(delegatingValidator);

        return jwtDecoder;
    }

}
