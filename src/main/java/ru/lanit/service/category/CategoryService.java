package ru.lanit.service.category;

import ru.lanit.model.entity.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);
    void saveAll(List<Category> categories);
    Category findById(Long id);
    List<Category> findAll();
    void deleteById(Long id);
    void delete(Category category);
    Category findByName(String name);
    List<Category> findAllParentCategories();
    List<Category> findAllSubsByParentCategory(Category parentCategory);
}
