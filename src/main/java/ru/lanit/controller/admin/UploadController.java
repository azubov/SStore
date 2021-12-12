package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.lanit.model.ImageSet;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.upload.UploadService;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/admin/uploadImage/new")
    public ModelAndView uploadImage(@RequestParam("imageFile") MultipartFile imageFile,
                                    @ModelAttribute("categoryName") String categoryName,
                                    @ModelAttribute("parentName") String parentName) {

        ModelAndView modelAndView = new ModelAndView();

        try {
            uploadService.saveImage(imageFile);
            modelAndView.addObject("categoryName", categoryName);
            modelAndView.addObject("imageSet", ImageSet.getImages());
            modelAndView.addObject("uploadedImageName", imageFile.getOriginalFilename());
            modelAndView.addObject("parentName", parentName);
            modelAndView.addObject("parentList", categoryService.findAllParentCategories());
            modelAndView.setViewName("admin/adminCategoryNew");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/admin/uploadImage/update")
    public String rateHandler(@RequestParam("imageFile") MultipartFile imageFile,
                              HttpServletRequest request) {

        try {
            uploadService.saveImage(imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }


}
