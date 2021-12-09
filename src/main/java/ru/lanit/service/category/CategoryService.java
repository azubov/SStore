package ru.lanit.service.category;

import org.springframework.web.multipart.MultipartFile;
import ru.lanit.model.entity.Category;

import java.io.IOException;
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

    void saveImage(MultipartFile imageFile) throws IOException;
}
