package ru.lanit.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.service.item.ItemService;

@Controller
@RequestMapping("/user/shopping")
public class UserShoppingController {

    private final ItemService itemService;

    @Autowired
    public UserShoppingController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "user/userItemList";
    }
}
