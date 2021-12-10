package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.lanit.model.entity.Category;
import ru.lanit.service.category.CategoryService;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    @Autowired
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String adminPage() {
        return "admin/adminPage";
    }

    @GetMapping("/category")
    public String showAll(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/adminListCategory";
    }

    @GetMapping("/category/new")
    public String showNewPage(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categoryList", categoryService.findAllParentCategories());
        return "admin/adminNewCategory";
    }

//    //    //    //    //    //    //    //    //    //    //    //    //    //    //    //    //    //

    @PostMapping("/category/new")
    public String create(Category category,
                         @ModelAttribute("parentName") String parentName,
                         @RequestParam("imageName") String imageName) {

        if (!parentName.isEmpty()) {
            category.setParentCategory(categoryService.findByName(parentName));
        }
        category.setImageUrl(imageName);
        categoryService.save(category);

        return "redirect:/admin/category";
    }

    @PostMapping("/category/uploadImage")
    public ModelAndView uploadImage(@RequestParam("imageFile") MultipartFile imageFile,
                                    Model model) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            categoryService.saveImage(imageFile);
            model.addAttribute("category", new Category());
            model.addAttribute("categoryList", categoryService.findAllParentCategories());
            model.addAttribute("imageName", imageFile.getOriginalFilename());
            System.out.println(imageFile.getOriginalFilename());
            modelAndView.setViewName("/admin/adminNewCategory");
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("/admin/adminNewCategory");
        }

        return modelAndView;
    }


    //    //    //    //    //    //    //    //    //    //    //    //    //    //    //    //    //    //

    @GetMapping("/category/update/{id}")
    public String showUpdatePage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("categoryList", categoryService.findAllParentCategories());
        return "admin/adminUpdateCategory";
    }

    @PostMapping("/category/update/{id}")
    public String update(Category category, @ModelAttribute("parentName") String parentName) {
        if (!parentName.isEmpty()) {
            category.setParentCategory(categoryService.findByName(parentName));
        }
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }




}
