package ru.lanit.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.model.dto.Color;
import ru.lanit.model.dto.ImageSet;
import ru.lanit.model.entity.Category;
import ru.lanit.model.entity.Item;
import ru.lanit.model.test.CategoryDto;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.item.ItemService;
import ru.lanit.service.upload.UploadService;

import java.io.IOException;

@Controller
public class UploadControllerTest {

    private final UploadService uploadService;
    private final CategoryService categoryService;
    private final ItemService itemService;

    @Autowired
    public UploadControllerTest(UploadService uploadService, CategoryService categoryService, ItemService itemService) {
        this.uploadService = uploadService;
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @PostMapping("/test/category/uploadImage/new")
    public String uploadImageCategoryNew(Model model, CategoryDto categoryDto,
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

        return "test/adminCategoryNewTest";
    }

    @PostMapping("/test/category/uploadImage/update/{id}")
    public String uploadImagCategoryUpdate(Model model,
                                           @PathVariable("id") Long id,
                                           @ModelAttribute("categoryName") String categoryName,
                                           @ModelAttribute("imageName") String imageName,
                                           @RequestParam("imageFile") MultipartFile imageFile,
                                           @ModelAttribute("parentName") String parentName) {

        try {
            uploadService.saveImage(imageFile);

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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "test/adminCategoryUpdateTEST";
    }

    @PostMapping("/test/item/uploadImage/new")
    public String uploadImageItemNew(Model model,
                                     @ModelAttribute("itemName") String itemName,
                                     @ModelAttribute("partNumber") String partNumber,
                                     @ModelAttribute("price") String price,
                                     @ModelAttribute("categoryName") String categoryName,
                                     @ModelAttribute("chosenColor") String chosenColor,
                                     @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            uploadService.saveImage(imageFile);

            model.addAttribute("itemName", itemName);
            model.addAttribute("partNumber", partNumber);
            model.addAttribute("price", price);

            model.addAttribute("categoryName", categoryName);
            model.addAttribute("categoryList", categoryService.findAllSubCategories());

            model.addAttribute("chosenColor", chosenColor);
            model.addAttribute("colors", Color.values());

            model.addAttribute("uploadedImageName", imageFile.getOriginalFilename());
            model.addAttribute("imageSet", ImageSet.getImages());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "test/adminItemNewTEST";
    }

    @PostMapping("/test/item/uploadImage/update/{id}")
    public String uploadImagCategoryUpdate(Model model,
                                           @PathVariable("id") Long id,
                                           @ModelAttribute("itemName") String itemName,
                                           @ModelAttribute("partNumber") String partNumber,
                                           @ModelAttribute("price") String price,
                                           @ModelAttribute("categoryName") String categoryName,
                                           @ModelAttribute("chosenColor") String chosenColor,
                                           @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            uploadService.saveImage(imageFile);
            Item itemFromDb = itemService.findById(id);

            model.addAttribute("imageSet", ImageSet.getImages());
            model.addAttribute("categoryList", categoryService.findAllSubCategories());
            model.addAttribute("colors", Color.values());
            model.addAttribute("id", id);

            model.addAttribute("itemName",
                    itemName.isEmpty() ? itemFromDb.getName() : itemName);
            model.addAttribute("partNumber",
                    partNumber.isEmpty() ? itemFromDb.getPartNumber() : partNumber);
            model.addAttribute("price",
                    price.isEmpty() ? itemFromDb.getPrice() : price);
            model.addAttribute("categoryName",
                    categoryName.isEmpty() ? itemFromDb.getCategory().getName() : categoryName);
            model.addAttribute("chosenColor",
                    chosenColor.isEmpty() ? itemFromDb.getColor() : chosenColor);
            model.addAttribute("uploadedImageName",
                    imageFile.isEmpty() ? itemFromDb.getImageUrl() : imageFile.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "test/adminItemUpdateTEST";

    }
}