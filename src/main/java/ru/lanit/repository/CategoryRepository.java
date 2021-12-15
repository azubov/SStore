package ru.lanit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lanit.model.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findFirstByName(String name);

    @Query("select c from Category c where c.parentCategory is null")
    List<Category> findAllParentCategories();

    @Query("select c from Category c where c.parentCategory = ?1")
    List<Category> findAllSubsByParentCategory(Category parentCategory);

    @Query("select c from Category c where c.parentCategory is not null")
    List<Category> findAllSubCategories();

    boolean existsCategoryByName(String name);
}
