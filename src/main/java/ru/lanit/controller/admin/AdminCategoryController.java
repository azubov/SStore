package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.ImageSet;
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
        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", categoryService.findAllParentCategories());
        model.addAttribute("categoryDto", categoryDto);
        return "admin/adminCategoryNew";
    }

    @PostMapping("/new")
    public String create(Model model, CategoryDto categoryDto) {

        if (categoryService.existsCategoryByName(categoryDto.getCategoryName())) {
            model.addAttribute("imageSet", ImageSet.getImages());
            model.addAttribute("parentList", categoryService.findAllParentCategories());
            model.addAttribute("categoryDto", categoryDto);
            model.addAttribute("nameError", "Category with such name already exists");
            return "admin/adminCategoryNew";
        }

        Category category = new Category();

        if (categoryDto.getParentName().isEmpty()) {
            category.populateWith(categoryDto, null);
        } else {
            Category parentCategory = categoryService.findByName(categoryDto.getParentName());
            category.populateWith(categoryDto, parentCategory);
        }

        categoryService.save(category);

        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(Model model, @PathVariable("id") Long id, CategoryDto categoryDto) {

        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", categoryService.findAllParentCategories());

        Category categoryFromDb = categoryService.findById(id);
        categoryDto.populateWith(categoryFromDb);
        model.addAttribute("categoryDto", categoryDto);

        return "admin/adminCategoryUpdate";
    }

    @PostMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") Long id, CategoryDto categoryDto) {

        Category category = categoryService.findById(id);

        if (categoryService.existsCategoryByName(categoryDto.getCategoryName())) {
            model.addAttribute("imageSet", ImageSet.getImages());
            model.addAttribute("parentList", categoryService.findAllParentCategories());
            categoryDto.populateWith(category);
            model.addAttribute("categoryDto", categoryDto);
            model.addAttribute("nameError", "Category with such name already exists");
            return "admin/adminCategoryUpdate";
        }

        if (categoryDto.getParentName().isEmpty()) {
            category.populateWith(categoryDto, null);
        } else {
            Category parentCategory = categoryService.findByName(categoryDto.getParentName());
            category.populateWith(categoryDto, parentCategory);
        }

        categoryService.save(category);

        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }

}
