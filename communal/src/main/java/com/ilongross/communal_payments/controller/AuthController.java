package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.UserAuthDto;
import com.ilongross.communal_payments.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/communal/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getMenu() {
        return "auth_menu";
    }

    @GetMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public String createForm(Model model) {
        model.addAttribute("user", UserAuthDto.builder().build());
        return "user_create_form";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute UserAuthDto userAuthDto, Model model) {
        var user = userAuthService.create(userAuthDto);
        var roles = String.join(",", user.getRoles());
        model.addAttribute("user", user);
        return "user_info";
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String loginForm(Model model) {
        model.addAttribute("user", UserAuthDto.builder().build());
        return "login_form";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String login(@ModelAttribute UserAuthDto userAuthDto, Model model) {
        var user = userAuthService.authenticate(userAuthDto);
        model.addAttribute("user", user);
        return "user_info";
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public String users(Model model) {
        var users = userAuthService.getAll();
        model.addAttribute("users", users);
        return "users_list";
    }

}
