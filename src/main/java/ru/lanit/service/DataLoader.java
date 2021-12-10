package ru.lanit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lanit.model.ImageSet;
import ru.lanit.model.criteria.Color;
import ru.lanit.model.entity.Category;
import ru.lanit.model.entity.Item;
import ru.lanit.service.category.CategoryService;
import ru.lanit.service.item.ItemService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataLoader {

    private final CategoryService categoryService;
    private final ItemService itemService;

    @Autowired
    public DataLoader(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
        loadRowsOfData(1);
        loadImages();
    }

    private void loadRowsOfData(int rows) {
        for (int i = rows; i > 0; i--) {
            loadParentCategories();
        }

        Category cars = categoryService.findByName("Cars");
        Category moto = categoryService.findByName("Moto");
        Category phones = categoryService.findByName("Phones");

        for (int i = rows; i > 0; i--) {
            loadChildCategories(cars, moto, phones);
        }

        List<Category> subCars = categoryService.findAllSubsByParentCategory(cars);
        List<Category> subMoto = categoryService.findAllSubsByParentCategory(moto);
        List<Category> subPhones = categoryService.findAllSubsByParentCategory(phones);

        subCars.forEach(this::loadItems);
        subMoto.forEach(this::loadItems);
        subPhones.forEach(this::loadItems);

    }

    private void loadParentCategories() {
        categoryService.saveAll(Stream.of(
                Category.builder().name("Cars").imageUrl("car.jpg").build(),
                Category.builder().name("Moto").imageUrl("moto.jpg").build(),
                Category.builder().name("Phones").imageUrl("phone.jpg").build()
        ).collect(Collectors.toList()));
    }

    private void loadChildCategories(Category cars, Category moto, Category phones) {
        categoryService.saveAll(Stream.of(
                Category.builder().name("Audi").imageUrl("au.jpg").parentCategory(cars).build(),
                Category.builder().name("BMW").imageUrl("bm.jpg").parentCategory(cars).build(),
                Category.builder().name("Mercedes-Benz").imageUrl("mb.jpg").parentCategory(cars).build(),
                Category.builder().name("Yamaha").imageUrl("ya.jpg").parentCategory(moto).build(),
                Category.builder().name("BMW").imageUrl("bm.jpg").parentCategory(moto).build(),
                Category.builder().name("Suzuki").imageUrl("su.jpg").parentCategory(moto).build(),
                Category.builder().name("Apple").imageUrl("ap.png").parentCategory(phones).build(),
                Category.builder().name("Samsung").imageUrl("sa.png").parentCategory(phones).build(),
                Category.builder().name("Xiaomi").imageUrl("xi.png").parentCategory(phones).build()
        ).collect(Collectors.toList()));
    }

    private void loadItems(Category category) {
        itemService.saveAll(Stream.of(
                Item.builder().name("3").partNumber("#333").price(30000.00).category(category).color(Color.WHITE.getDisplayValue()).imageUrl(category.getImageUrl()).build(),
                Item.builder().name("5").partNumber("#555").price(40000.00).category(category).color(Color.BLACK.getDisplayValue()).imageUrl(category.getImageUrl()).build(),
                Item.builder().name("7").partNumber("#777").price(50000.00).category(category).color(Color.GREEN.getDisplayValue()).imageUrl(category.getImageUrl()).build()
        ).collect(Collectors.toList()));
    }

    private void loadImages() {
        ImageSet.addAllImages(
                "car.jpg",
                "moto.jpg",
                "phone.jpg",
                "au.jpg",
                "bm.jpg",
                "mb.jpg",
                "ya.jpg",
                "bm.jpg",
                "su.jpg",
                "ap.png",
                "sa.png",
                "xi.png"
        );
    }
}
