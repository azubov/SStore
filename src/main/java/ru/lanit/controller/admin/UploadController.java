package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.model.dto.Color;
import ru.lanit.model.dto.ImageSet;
import ru.lanit.model.dto.ItemDto;
import ru.lanit.model.entity.Category;
import ru.lanit.model.entity.Item;
import ru.lanit.model.dto.CategoryDto;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.item.ItemService;
import ru.lanit.service.upload.UploadService;

import java.io.IOException;

@Controller
public class UploadController {

    private final UploadService uploadService;
    private final CategoryService categoryService;
    private final ItemService itemService;

    @Autowired
    public UploadController(UploadService uploadService, CategoryService categoryService, ItemService itemService) {
        this.uploadService = uploadService;
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @PostMapping("/admin/category/uploadImage/new")
    public String uploadImageCategoryNew(Model model,
                                         CategoryDto categoryDto,
                                         @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            uploadService.saveImage(imageFile);

            model.addAttribute("imageSet", ImageSet.getImages());
            model.addAttribute("parentList", categoryService.findAllParentCategories());

            categoryDto.setUploadedImageName(imageFile.getOriginalFilename());
            model.addAttribute("categoryDto", categoryDto);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "admin/adminCategoryNew";
    }

    @PostMapping("/admin/category/uploadImage/update/{id}")
    public String uploadImagCategoryUpdate(Model model,
                                           @PathVariable("id") Long id,
                                           CategoryDto categoryDto,
                                           @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            uploadService.saveImage(imageFile);

            model.addAttribute("imageSet", ImageSet.getImages());
            model.addAttribute("parentList", categoryService.findAllParentCategories());
            model.addAttribute("id", id);

            Category categoryFromDb = categoryService.findById(id);
            categoryDto.populateWith(categoryFromDb);
            categoryDto.setUploadedImageName(imageFile.getOriginalFilename());
            model.addAttribute("categoryDto", categoryDto);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "admin/adminCategoryUpdate";
    }

    @PostMapping("/admin/item/uploadImage/new")
    public String uploadImageItemNew(Model model,
                                     ItemDto itemDto,
                                     @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            uploadService.saveImage(imageFile);

            model.addAttribute("categoryList", categoryService.findAllSubCategories());
            model.addAttribute("colors", Color.values());
            model.addAttribute("imageSet", ImageSet.getImages());

            itemDto.setUploadedImageName(imageFile.getOriginalFilename());
            model.addAttribute("itemDto", itemDto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "admin/adminItemNew";
    }

    @PostMapping("/admin/item/uploadImage/update/{id}")
    public String uploadImagCategoryUpdate(Model model,
                                           @PathVariable("id") Long id,
                                           ItemDto itemDto,
                                           @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            uploadService.saveImage(imageFile);
            Item itemFromDb = itemService.findById(id);
            itemDto.populateWith(itemFromDb);
            itemDto.setUploadedImageName(imageFile.getOriginalFilename());

            model.addAttribute("imageSet", ImageSet.getImages());
            model.addAttribute("categoryList", categoryService.findAllSubCategories());
            model.addAttribute("colors", Color.values());
            model.addAttribute("itemDto", itemDto);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "admin/adminItemUpdate";

    }
}
