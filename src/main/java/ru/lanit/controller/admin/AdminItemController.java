package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.service.item.ItemService;

@Controller
@RequestMapping("/admin/item")
public class AdminItemController {

    private final ItemService service;

    @Autowired
    public AdminItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("items", service.findAll());
        return "admin/adminItemList";
    }

}
