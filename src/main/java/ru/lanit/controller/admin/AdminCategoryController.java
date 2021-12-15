package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.ImageSet;
import ru.lanit.model.entity.Category;
import ru.lanit.model.test.CategoryDto;
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
    public String create(CategoryDto categoryDto) {

        Category category = new Category();
        category.setName(categoryDto.getCategoryName());
        category.setImageUrl(categoryDto.getUploadedImageName());

        if (!categoryDto.getParentName().isEmpty()) {
            category.setParentCategory(categoryService.findByName(categoryDto.getParentName()));
        }

        categoryService.save(category);

        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(Model model,
                                 @PathVariable("id") Long id,
                                 CategoryDto categoryDto) {

        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", categoryService.findAllParentCategories());

        Category categoryFromDb = categoryService.findById(id);
        categoryDto.populateWith(categoryFromDb);

        model.addAttribute("categoryDto", categoryDto);

        return "admin/adminCategoryUpdate";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         CategoryDto categoryDto) {

        Category category = categoryService.findById(id);
        category.setName(categoryDto.getCategoryName());
        category.setImageUrl(categoryDto.getUploadedImageName());

        if (!categoryDto.getParentName().isEmpty()) {
            category.setParentCategory(categoryService.findByName(categoryDto.getParentName()));
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
