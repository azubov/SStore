package ru.lanit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lanit.model.entity.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where i.category.parentCategory.name = ?1 and i.category.name = ?2")
    List<Item> findAllItemsBySubCategory(String parentCategory, String subCategoryName);
}
