package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.model.security.Role;
import ru.lanit.model.security.User;
import ru.lanit.service.security.UserService;

@Controller
@RequestMapping("/auth")
public class SecurityController {

    private final UserService service;

    @Autowired
    public SecurityController(UserService service) {
        this.service = service;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "security/registration";
    }

    @PostMapping("/registration")
    public String register(User user) {
        service.save(user);
        return "security/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "security/login";
    }

    @PostMapping
    public String login(User user) {
        if (service.verify(user)) {
            return "criteriaList";
        } else {
            return "security/login";
        }

    }



}
