package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.model.criteria.Color;
import ru.lanit.model.criteria.ItemPage;
import ru.lanit.model.criteria.ItemSearchCriteria;
import ru.lanit.service.item.ItemService;

@Controller
@RequestMapping("/")
public class ItemController {

    private final ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public String allItems(ItemPage itemPage,
                           ItemSearchCriteria itemSearchCriteria,
                           Model model) {
        model.addAttribute("criteria", service.getItems(itemPage, itemSearchCriteria));
        model.addAttribute("colors", Color.values());
        model.addAttribute("itemPage", itemPage);
        return "criteriaList";
    }

    @GetMapping("/category/{parentCategoryName}/{subCategoryName}")
    public String allItemsBySubCategory(@PathVariable("subCategoryName") String subCategoryName,
                                        @PathVariable("parentCategoryName") String parentCategory,
                                        Model model) {
        model.addAttribute("items", service.findAllItemsBySubCategory(parentCategory, subCategoryName));
        return "listItems";
    }

}
