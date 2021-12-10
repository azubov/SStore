package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.ImageSet;
import ru.lanit.model.entity.Category;
import ru.lanit.service.category.CategoryService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    @Autowired
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String adminPage() {
        return "admin/adminPage";
    }

    @GetMapping("/category")
    public String showAll(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/adminListCategory";
    }

    @GetMapping("/category/new")
    public String showNewPage(Model model) {
        model.addAttribute("parentList", categoryService.findAllParentCategories());
        model.addAttribute("imageSet", ImageSet.getImages());
        return "admin/adminNewCategory";
    }

    @PostMapping("/category/new")
    public String create(@ModelAttribute("categoryName") String categoryName,
                         @ModelAttribute("parentName") String parentName,
                         @RequestParam("imageName") String imageName) {

        Category category = new Category();
        category.setName(categoryName);
        category.setImageUrl(imageName);

        if (!parentName.isEmpty()) {
            category.setParentCategory(categoryService.findByName(parentName));
        }

        categoryService.save(category);

        return "redirect:/admin/category";
    }

    @GetMapping("/category/update/{id}")
    public String showUpdatePage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("categoryList", categoryService.findAllParentCategories());
        return "admin/adminUpdateCategory";
    }

    @PostMapping("/category/update/{id}")
    public String update(Category category, @ModelAttribute("parentName") String parentName) {
        if (!parentName.isEmpty()) {
            category.setParentCategory(categoryService.findByName(parentName));
        }
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }

}
