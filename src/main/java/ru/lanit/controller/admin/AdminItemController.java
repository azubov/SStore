package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.ImageSet;
import ru.lanit.model.dto.Color;
import ru.lanit.model.dto.ItemDto;
import ru.lanit.model.entity.Category;
import ru.lanit.model.entity.Item;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.item.ItemService;

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
        model.addAttribute("categoryList", categoryService.findAllSubCategories());
        model.addAttribute("colors", Color.values());
        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("itemDto", itemDto);
        return "admin/adminItemNew";
    }

    @PostMapping("/new")
    public String create(ItemDto itemDto) {

        Category category = categoryService.findByName(itemDto.getCategoryName());
        Item item = new Item();
        item.populateWith(itemDto, category);
        itemService.save(item);

        return "redirect:/admin/item";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(Model model,
                                 @PathVariable("id") Long id,
                                 ItemDto itemDto) {

        Item itemFromDb = itemService.findById(id);

        itemDto.populateWith(itemFromDb);

        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("categoryList", categoryService.findAllSubCategories());
        model.addAttribute("colors", Color.values());
        model.addAttribute("itemDto", itemDto);

        return "admin/adminItemUpdate";
    }

    @PostMapping("/update/{id}")
    public String update(
                         @PathVariable("id") Long id,
                         ItemDto itemDto,
                         @ModelAttribute("uploadedImageName") String uploadedImageName) {

        Item item = itemService.findById(id);
        Category category = categoryService.findByName(itemDto.getCategoryName());
        item.populateWith(itemDto, category);
        itemService.save(item);

        return "redirect:/admin/item";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        itemService.deleteById(id);
        return "redirect:/admin/item";
    }

}
