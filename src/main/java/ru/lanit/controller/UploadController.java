package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.lanit.model.entity.Category;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.upload.UploadService;

import java.io.IOException;
import java.util.List;

@Controller
public class UploadController {

    private final UploadService uploadService;
    private final CategoryService categoryService;

    @Autowired
    public UploadController(UploadService uploadService, CategoryService categoryService) {
        this.uploadService = uploadService;
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/category/uploadImage")
    public ModelAndView uploadImage(@RequestParam("imageFile") MultipartFile imageFile,
                                    @ModelAttribute("categoryName") String categoryName,
                                    @ModelAttribute("parentName") String parentName) {

        ModelAndView modelAndView = new ModelAndView();

        try {
            uploadService.saveImage(imageFile);
            modelAndView.addObject("categoryName", categoryName);
            modelAndView.addObject("parentName", parentName);
            modelAndView.addObject("parentList", categoryService.findAllParentCategories());
            modelAndView.addObject("imageName", imageFile.getOriginalFilename());
            modelAndView.setViewName("/admin/adminNewCategory");
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("/admin/adminNewCategory");
        }

        return modelAndView;
    }
}