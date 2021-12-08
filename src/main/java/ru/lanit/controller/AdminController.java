package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "admin/adminPage";
    }

    @GetMapping("/category")
    public String showAll(Model model) {
        model.addAttribute("categories", service.findAll());
        return "admin/adminListCategory";
    }

    @GetMapping("/category/new")
    public String showNewPage(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categoryList", service.findAllParentCategories());
        return "admin/adminNewCategory";
    }

    @PostMapping("/category/new")
    public String create(Category category, @ModelAttribute("parentName") String parentName) {
        if (!parentName.isEmpty()) {
            category.setParentCategory(service.findByName(parentName));
        }
        service.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/update/{id}")
    public String showUpdatePage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", service.findById(id));
        model.addAttribute("categoryList", service.findAllParentCategories());
        return "admin/adminUpdateCategory";
    }

    @PostMapping("/category/update/{id}")
    public String update(Category category, @ModelAttribute("parentName") String parentName) {
        if (!parentName.isEmpty()) {
            category.setParentCategory(service.findByName(parentName));
        }
        service.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/admin/category";
    }



}
