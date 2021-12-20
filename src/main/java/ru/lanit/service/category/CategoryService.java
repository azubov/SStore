package ru.lanit.service.category;

import org.springframework.ui.Model;
import ru.lanit.model.dto.CategoryDto;
import ru.lanit.model.entity.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);
    void saveAll(List<Category> categories);
    Category findById(Long id);
    List<Category> findAll();
    void deleteById(Long id);
    Category findByName(String name);
    List<Category> findAllParentCategories();
    List<Category> findAllSubsByParentCategory(Category parentCategory);
    List<Category> findAllSubCategories();
    List<String> displaySubCategoryUniqueNames();
    boolean existsCategoryByName(String name);

    void bindCategory(Model model, CategoryDto categoryDto);
    void bindWithError(Model model, CategoryDto categoryDto);
    void populateParentCategory(CategoryDto categoryDto, Category category);
}
