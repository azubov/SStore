package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.lanit.model.dto.Color;
import ru.lanit.model.criteria.ItemPage;
import ru.lanit.model.criteria.ItemSearchCriteria;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.item.ItemService;


@Controller
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String allItems(ItemPage itemPage,
                           ItemSearchCriteria itemSearchCriteria,
                           Model model) {
        model.addAttribute("criteria", itemService.getItems(itemPage, itemSearchCriteria));
        model.addAttribute("colors", Color.values());
        model.addAttribute("itemPage", itemPage);
        model.addAttribute("categories", categoryService.displaySubCategoryUniqueNames());
        model.addAttribute("parentCategories", categoryService.findAllParentCategories());

        return "criteriaList";
    }

    @GetMapping("/category/{parentCategoryName}/{subCategoryName}")
    public String allItemsBySubCategory(@PathVariable("subCategoryName") String subCategoryName,
                                        @PathVariable("parentCategoryName") String parentCategory,
                                        Model model) {
        model.addAttribute("items", itemService.findAllItemsBySubCategory(parentCategory, subCategoryName));
        return "listItems";
    }

}
