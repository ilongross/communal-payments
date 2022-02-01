package com.ilongross.communal_payments.security.jwt;

public interface JwtProvider {

    String generateJwt(String username);
    boolean validateJwt(String token);
    String getUsernameFromJwt(String token);

}
