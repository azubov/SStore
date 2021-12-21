package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.ItemDto;
import ru.lanit.model.entity.Category;
import ru.lanit.model.entity.Item;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.item.ItemService;

import java.util.List;

@Controller
@RequestMapping("/admin/item")
public class AdminItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @Autowired
    public AdminItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "admin/adminItemList";
    }

    @GetMapping("/new")
    public String showNewPage(Model model, ItemDto itemDto) {

        List<String> categoryList = categoryService.displaySubCategoryUniqueNames();

        itemService.bindItem(model, itemDto, categoryList);

        return "admin/adminItemNew";
    }

    @PostMapping("/new")
    public String create(ItemDto itemDto) {

        String categoryFromInput = itemDto.getCategoryName();
        Category category = categoryService.findByName(categoryFromInput);

        Item item = itemService.createItemFrom(itemDto, category);

        itemService.save(item);

        return "redirect:/admin/item";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(Model model, @PathVariable("id") Long id, ItemDto itemDto) {

        itemService.bindDto(itemDto, id);

        List<String> categoryList = categoryService.displaySubCategoryUniqueNames();
        itemService.bindItem(model, itemDto, categoryList);

        return "admin/adminItemUpdate";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, ItemDto itemDto,
                         @ModelAttribute("uploadedImageName") String uploadedImageName) {

        String categoryFromInput = itemDto.getCategoryName();
        Category category = categoryService.findByName(categoryFromInput);

        Item item = itemService.findItemAndPopulateWith(id, itemDto, category);

        itemService.save(item);

        return "redirect:/admin/item";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        itemService.deleteById(id);
        return "redirect:/admin/item";
    }

}
