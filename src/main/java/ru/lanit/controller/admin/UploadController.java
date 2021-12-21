package ru.lanit.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.errors.ErrorType;
import ru.lanit.model.dto.ItemDto;
import ru.lanit.model.dto.CategoryDto;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.item.ItemService;
import ru.lanit.service.upload.UploadService;

import java.io.IOException;
import java.util.List;

@Slf4j
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
    public String uploadImageCategoryNew(Model model, CategoryDto categoryDto,
                                         @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            uploadService.saveImage(imageFile);

            categoryService.bindCategoryWithImage(model, categoryDto, imageFile.getOriginalFilename());

        } catch (IOException e) {
            log.error(ErrorType.IMAGE_NOT_SAVED.getDescription(), e.getMessage());
            e.printStackTrace();
        }

        return "admin/adminCategoryNew";
    }

    @PostMapping("/admin/category/uploadImage/update/{id}")
    public String uploadImagCategoryUpdate(Model model, @PathVariable("id") Long id, CategoryDto categoryDto,
                                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            uploadService.saveImage(imageFile);

            categoryService.bindDto(categoryDto, id);
            categoryService.bindCategoryWithImage(model, categoryDto, imageFile.getOriginalFilename());

        } catch (IOException e) {
            log.error(ErrorType.IMAGE_NOT_SAVED.getDescription(), e.getMessage());
            e.printStackTrace();
        }

        return "admin/adminCategoryUpdate";
    }

    @PostMapping("/admin/item/uploadImage/new")
    public String uploadImageItemNew(Model model, ItemDto itemDto,
                                     @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            uploadService.saveImage(imageFile);

            itemService.bindDtoWithImage(itemDto, imageFile.getOriginalFilename());

            List<String> categoryList = categoryService.displaySubCategoryUniqueNames();
            itemService.bindItem(model, itemDto, categoryList);

        } catch (IOException e) {
            log.error(ErrorType.IMAGE_NOT_SAVED.getDescription(), e.getMessage());
            e.printStackTrace();
        }

        return "admin/adminItemNew";
    }

    @PostMapping("/admin/item/uploadImage/update/{id}")
    public String uploadImagCategoryUpdate(Model model, @PathVariable("id") Long id, ItemDto itemDto,
                                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            uploadService.saveImage(imageFile);

            itemService.bindDto(itemDto, id);
            itemService.bindDtoWithImage(itemDto, imageFile.getOriginalFilename());

            List<String> categoryList = categoryService.displaySubCategoryUniqueNames();
            itemService.bindItem(model, itemDto, categoryList);

        } catch (IOException e) {
            log.error(ErrorType.IMAGE_NOT_SAVED.getDescription(), e.getMessage());
            e.printStackTrace();
        }

        return "admin/adminItemUpdate";

    }
}
