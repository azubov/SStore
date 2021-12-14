package ru.lanit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lanit.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findFirstByName(String name);
}
