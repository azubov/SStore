package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.ImageSet;
import ru.lanit.model.entity.Category;
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
    public String showNewPage(Model model,
                              @ModelAttribute("categoryName") String categoryName,
                              @ModelAttribute("uploadedImageName") String uploadedImageName,
                              @ModelAttribute("parentName") String parentName
                              ) {
        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", categoryService.findAllParentCategories());

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("uploadedImageName", uploadedImageName);
        model.addAttribute("parentName", parentName);

        return "admin/adminCategoryNew";
    }

    @PostMapping("/new")
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

    @GetMapping("/update/{id}")
    public String showUpdatePage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("parentList", categoryService.findAllParentCategories());
        model.addAttribute("imageSet", ImageSet.getImages());
        return "admin/adminCategoryUpdate";
    }

    @PostMapping("/update/{id}")
    public String update(Category category, @ModelAttribute("parentName") String parentName) {
        if (!parentName.isEmpty()) {
            category.setParentCategory(categoryService.findByName(parentName));
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
