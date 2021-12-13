package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.model.ImageSet;
import ru.lanit.model.entity.Category;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.upload.UploadService;

import java.io.IOException;

@Controller
public class UploadController {

    private final UploadService uploadService;
    private final CategoryService categoryService;

    @Autowired
    public UploadController(UploadService uploadService, CategoryService categoryService) {
        this.uploadService = uploadService;
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/category/uploadImage/new")
    public String uploadImage(Model model,
                              @ModelAttribute("categoryName") String categoryName,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @ModelAttribute("parentName") String parentName
                              ) {
        try {
            uploadService.saveImage(imageFile);

            model.addAttribute("imageSet", ImageSet.getImages());
            model.addAttribute("parentList", categoryService.findAllParentCategories());


            model.addAttribute("categoryName", categoryName);
            model.addAttribute("uploadedImageName", imageFile.getOriginalFilename());
            model.addAttribute("parentName", parentName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "admin/adminCategoryNew";
    }

    @PostMapping("/admin/category/uploadImage/update/{id}")
    public String rateHandler(Model model,
                              @PathVariable("id") Long id,
                              @ModelAttribute("categoryName") String categoryName,
                              @ModelAttribute("imageName") String imageName,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @ModelAttribute("parentName") String parentName
                              ) {

        try {
            uploadService.saveImage(imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", categoryService.findAllParentCategories());
        model.addAttribute("id", id);

        Category categoryFromDb = categoryService.findById(id);

        model.addAttribute("categoryName",
                categoryName.isEmpty() ? categoryFromDb.getName() : categoryName);
        model.addAttribute("uploadedImageName",
                imageName.isEmpty() ? categoryFromDb.getImageUrl() : imageFile.getOriginalFilename());
        model.addAttribute("parentName",
                parentName.isEmpty() ? categoryFromDb.getParentCategory() : parentName);

        return "admin/adminCategoryUpdate";
    }


}
