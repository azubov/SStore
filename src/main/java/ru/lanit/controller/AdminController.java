package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.model.entity.Category;
import ru.lanit.service.category.CategoryService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService service;

    @Autowired
    public AdminController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String adminPage() {
        return "adminPage";
    }

    @GetMapping("/category")
    public String show(Model model) {
        model.addAttribute("categories", service.findAll());
        return "adminListCategory";
    }

    @GetMapping("/category/new")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categoryList", service.findAllParentCategories());
        return "adminNewCategory";
    }

    @PostMapping("/category/new")
    public String update(Category category, @ModelAttribute("parentName") String parentName) {
        if (parentName.isEmpty()) {
            category.setParentCategory(service.findByName(parentName));
        }
        service.save(category);
        return "redirect:/admin/category";
    }

}
