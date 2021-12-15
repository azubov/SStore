package ru.lanit.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.ImageSet;
import ru.lanit.model.entity.Category;
import ru.lanit.model.dto.CategoryDto;
import ru.lanit.service.category.CategoryService;

@Controller
@RequestMapping("/test")
public class AdminCategoryControllerTest {

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryControllerTest(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "test/adminCategoryListTest";
    }

    @GetMapping("/new")
    public String showNewPage(Model model, CategoryDto categoryDto) {
        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", categoryService.findAllParentCategories());
        model.addAttribute("categoryDto", categoryDto == null ? new CategoryDto() : categoryDto);

        return "test/adminCategoryNewTest";
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

        return "redirect:/test";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(Model model,
                                 @PathVariable("id") Long id,
                                 @ModelAttribute("categoryName") String categoryName,
                                 @ModelAttribute("uploadedImageName") String uploadedImageName,
                                 @ModelAttribute("parentName") String parentName) {

        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", categoryService.findAllParentCategories());
        model.addAttribute("id", id);

        Category categoryFromDb = categoryService.findById(id);

        model.addAttribute("categoryName",
                categoryName.isEmpty() ? categoryFromDb.getName() : categoryName);
        model.addAttribute("uploadedImageName",
                uploadedImageName.isEmpty() ? categoryFromDb.getImageUrl() : uploadedImageName);
        model.addAttribute("parentName",
                parentName.isEmpty() ? categoryFromDb.getParentCategoryName() : parentName);

        return "test/adminCategoryUpdateTEST";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("categoryName") String categoryName,
                         @ModelAttribute("imageName") String imageName,
                         @ModelAttribute("parentName") String parentName) {

        Category category = categoryService.findById(id);
        category.setName(categoryName);
        category.setImageUrl(imageName);

        if (!parentName.isEmpty()) {
            category.setParentCategory(categoryService.findByName(parentName));
        }

        categoryService.save(category);

        return "redirect:/test/categoryTEST";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/test/categoryTEST";
    }

}
