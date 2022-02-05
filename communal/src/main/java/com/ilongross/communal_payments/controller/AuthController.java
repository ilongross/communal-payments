package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.UserAuthDto;
import com.ilongross.communal_payments.security.jwt.JwtProvider;
import com.ilongross.communal_payments.service.UserAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

//@RestController
//@RequestMapping("/communal/auth")
public class AuthController {

    private final UserAuthService userAuthService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UserAuthService userAuthService,
            JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userAuthService = userAuthService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/create_user")
    public String createUserAuth(@RequestBody UserAuthDto userAuthDto) {
        return userAuthService.create(userAuthDto);
    }


    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String logpass) {

        var logpassArray = new String(
                Base64.getUrlDecoder().decode(logpass.substring(6)), StandardCharsets.UTF_8)
                .split(":");
        var login = logpassArray[0];
        var password = logpassArray[1];

        var authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        var jwt = jwtProvider.generateJwt(login);
        return ResponseEntity
                .ok()
                .header("access_token", jwt)
                .build();
    }

}
