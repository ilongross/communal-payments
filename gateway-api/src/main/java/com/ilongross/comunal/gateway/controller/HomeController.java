package com.ilongross.comunal.gateway.controller;

import com.ilongross.comunal.gateway.service.CommunalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private CommunalApiService communalApiService;

    @GetMapping("/")
    public String getHome(Model model, @AuthenticationPrincipal OidcUser principal) {
        if(principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }

    @GetMapping("/all-communal-accounts")
    public @ResponseBody String getAllExchange() {
        return communalApiService.getAllExchange();
    }


}
