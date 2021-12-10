package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.lanit.model.entity.Category;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.image.ImageService;

import java.io.IOException;

@Controller
public class UploadController {

    private final ImageService imageService;
    private final CategoryService categoryService;

    @Autowired
    public UploadController(ImageService imageService, CategoryService categoryService) {
        this.imageService = imageService;
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/category/uploadImage")
    public ModelAndView uploadImage(@RequestParam("imageFile") MultipartFile imageFile,
                                    Model model) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            imageService.saveImage(imageFile);
            model.addAttribute("category", new Category());
            model.addAttribute("categoryList", categoryService.findAllParentCategories());
            model.addAttribute("imageName", imageFile.getOriginalFilename());
            modelAndView.setViewName("/admin/adminNewCategory");
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("/admin/adminNewCategory");
        }

        return modelAndView;
    }
}
