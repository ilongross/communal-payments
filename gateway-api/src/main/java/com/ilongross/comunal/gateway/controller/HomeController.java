package com.ilongross.comunal.gateway.controller;

import com.ilongross.comunal.gateway.service.CommunalApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final CommunalApiService communalApiService;

    @GetMapping("/")
    public String getHome(Model model, @AuthenticationPrincipal OidcUser principal) {
        if(principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }

    @GetMapping("/all-communal-accounts")
    public @ResponseBody String getAllAccounts() {
        return communalApiService.getAllAccounts();
    }

    @GetMapping("/debtors")
    public @ResponseBody String getDebtors() {
        return communalApiService.getDebtors();
    }


}
