package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lanit.model.entity.Role;
import ru.lanit.model.entity.User;
import ru.lanit.service.user.UserService;

@Controller
public class RegistrationController {

    private final UserService service;

    @Autowired
    public RegistrationController(UserService service) {
        this.service = service;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "security/registration";
    }

    @PostMapping("/registration")
    public String register(User user) {
        service.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", service.findAll());
        return "security/users";
    }
}
