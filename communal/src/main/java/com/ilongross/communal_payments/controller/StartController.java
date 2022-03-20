package com.ilongross.communal_payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/communal/start")
public class StartController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String mainPage() {
        return "start_page";
    }

}
