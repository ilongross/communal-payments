package com.ilongross.comunal.gateway.service;

public interface TokenApiService {

    String getToken(String clientId, String clientSecret, String audience);

}
