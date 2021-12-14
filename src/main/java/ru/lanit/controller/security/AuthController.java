package ru.lanit.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "security/login";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage() {
        return "security/accessDenied";
    }

}
