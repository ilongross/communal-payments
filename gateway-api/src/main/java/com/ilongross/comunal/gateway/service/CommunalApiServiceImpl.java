package com.ilongross.comunal.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CommunalApiServiceImpl implements CommunalApiService {

    @Value("${spring.security.oauth2.client.registration.auth0.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.auth0.client-secret}")
    private String clientSecret;
    @Value("${service.communal.audience}")
    private String audience;

    @Value("${service.communal.uri}")
    private String uri;
    @Value("${service.communal.path.communal-service}")
    private String communalPath;
    @Value("${service.communal.path.communal-debtors}")
    private String debtorsPath;


    private final RestTemplate restTemplate;
    private final TokenApiService tokenApiService;

    @Override
    public String getAllAccounts() {
        var token = tokenApiService.getToken(clientId, clientSecret, audience);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        var requestEntity =
                RequestEntity
                        .get(uri + communalPath)
                        .headers(httpHeaders)
                        .build();
        var responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public String getDebtors() {
        var token = tokenApiService.getToken(clientId, clientSecret, audience);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        var requestEntity =
                RequestEntity
                        .get(uri + debtorsPath)
                        .headers(httpHeaders)
                        .build();
        var responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }
}
