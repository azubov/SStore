package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.entity.Category;
import ru.lanit.model.dto.CategoryDto;
import ru.lanit.service.category.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/adminCategoryList";
    }

    @GetMapping("/new")
    public String showNewPage(Model model, CategoryDto categoryDto) {
        categoryService.bindCategory(model, categoryDto);
        return "admin/adminCategoryNew";
    }

    @PostMapping("/new")
    public String create(Model model, CategoryDto categoryDto) {

        String nameFromInput = categoryDto.getCategoryName();

        if (categoryService.existsCategoryByName(nameFromInput)) {
            categoryService.bindWithError(model, categoryDto);
             return "admin/adminCategoryNew";
        }

        Category category = new Category();
        categoryService.populateParentCategory(categoryDto, category);
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(Model model, @PathVariable("id") Long id, CategoryDto categoryDto) {

        Category categoryFromDb = categoryService.findById(id);
        categoryDto.populateWith(categoryFromDb);
        categoryService.bindCategory(model, categoryDto);
        return "admin/adminCategoryUpdate";
    }

    @PostMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") Long id, CategoryDto categoryDto) {

        Category category = categoryService.findById(id);
        String nameFromInput = categoryDto.getCategoryName();
        String currentName = category.getName();

        if (categoryService.existsCategoryByName(nameFromInput) && !nameFromInput.equals(currentName)) {
            categoryService.bindWithError(model, categoryDto);
            categoryDto.populateWith(category);
            return "admin/adminCategoryUpdate";
        }

        categoryService.populateParentCategory(categoryDto, category);
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }

}
