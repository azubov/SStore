package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.model.entity.Category;
import ru.lanit.service.category.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String allCategories(Model model) {
        model.addAttribute("categories", service.findAllParentCategories());
        return "listCategories";
    }

    @GetMapping("/{categoryName}")
    public String allSubCategories(@PathVariable("categoryName") String categoryName, Model model) {
        Category parentCategory = service.findByName(categoryName);
        model.addAttribute("categories", service.findAllSubsByParentCategory(parentCategory));
        return "listSubCategories";
    }



}
