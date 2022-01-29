package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.UserAuthDto;
import com.ilongross.communal_payments.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communal/auth")
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/create_user")
    public String createUserAuth(@RequestBody UserAuthDto userAuthDto) {
        return userAuthService.create(userAuthDto);
    }


}
