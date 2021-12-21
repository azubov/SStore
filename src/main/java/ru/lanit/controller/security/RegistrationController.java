package ru.lanit.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lanit.errors.ErrorType;
import ru.lanit.model.entity.Role;
import ru.lanit.model.entity.User;
import ru.lanit.service.user.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "security/registration";
    }

    @PostMapping("/registration")
    public String register(User user, Model model) {
        if (userService.existsUserByName(user.getName())) {
            model.addAttribute("nameError", ErrorType.NAME_ALREADY_EXISTS.getDescription());
            model.addAttribute("user", new User());
            model.addAttribute("roles", Role.values());
            return "security/registration";
        }
        userService.save(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "security/users";
    }
}
