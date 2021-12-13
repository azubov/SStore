package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.model.ImageSet;
import ru.lanit.model.criteria.Color;
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
    public String showNewPage(Model model,
                              @ModelAttribute("itemName") String itemName,
                              @ModelAttribute("partNumber") String partNumber,
                              @ModelAttribute("price") String price,
                              @ModelAttribute("categoryName") String categoryName,
                              @ModelAttribute("chosenColor") String chosenColor,
                              @ModelAttribute("uploadedImageName") String uploadedImageName
                              ) {

        model.addAttribute("itemName", itemName);
        model.addAttribute("partNumber", partNumber);
        model.addAttribute("price", price);

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("categoryList", categoryService.findAllSubCategories());

        model.addAttribute("chosenColor", chosenColor);
        model.addAttribute("colors", Color.values());

        model.addAttribute("uploadedImageName", uploadedImageName);
        model.addAttribute("imageSet", ImageSet.getImages());

        return "admin/adminItemNew";
    }

    @PostMapping("/new")
    public String create(Model model,
                              @ModelAttribute("itemName") String itemName,
                              @ModelAttribute("partNumber") String partNumber,
                              @ModelAttribute("price") String price,
                              @ModelAttribute("categoryName") String categoryName,
                              @ModelAttribute("chosenColor") String chosenColor,
                              @ModelAttribute("uploadedImageName") String uploadedImageName
    ) {

        Category category = categoryService.findByName(categoryName);

        Item item = new Item();
        item.setName(itemName);
        item.setPartNumber(partNumber);
        item.setPrice(Double.parseDouble(price));
        item.setCategory(category);
        item.setColor(chosenColor);
        item.setImageUrl(uploadedImageName);

        itemService.save(item);

        return "redirect:/admin/item";
    }

}
