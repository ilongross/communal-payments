package com.ilongross.communal_payments.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
@Slf4j
class JwtProviderImpl implements JwtProvider {

    @Value("${security.jwt.expiration}")
    private Integer expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public String generateJwt(String username) {

        var expirationDate = Date.from(Instant.now().plusSeconds(expiration));

        return Jwts.builder()
                .claim(Claims.SUBJECT, username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public boolean validateJwt(String token) {

        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT.", e);
        }
        return false;
    }

    @Override
    public String getUsernameFromJwt(String token) {
        var claimsJws = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

}
