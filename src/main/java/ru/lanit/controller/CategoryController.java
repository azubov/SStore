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

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String allCategories(Model model) {
        model.addAttribute("categories", categoryService.findAllParentCategories());
        return "listCategories";
    }

    @GetMapping("/{categoryName}")
    public String allSubCategories(@PathVariable("categoryName") String categoryName, Model model) {
        Category parentCategory = categoryService.findByName(categoryName);
        model.addAttribute("categories", categoryService.findAllSubsByParentCategory(parentCategory));
        return "listSubCategories";
    }



}
