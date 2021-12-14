package ru.lanit.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.model.entity.Category;
import ru.lanit.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Category category) {
        repository.save(category);
    }

    @Override
    public void saveAll(List<Category> categories) {
        repository.saveAll(categories);
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = repository.findById(id);
        return category.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Category findByName(String name) {
        return repository.findFirstByName(name);
    }

    @Override
    public List<Category> findAllParentCategories() {
        return repository.findAllParentCategories();
    }

    @Override
    public List<Category> findAllSubsByParentCategory(Category parentCategory) {
        return repository.findAllSubsByParentCategory(parentCategory);
    }

    @Override
    public List<Category> findAllSubCategories() {
        return repository.findAll()
                .stream()
                .filter(category -> category.getParentCategory() != null)
                .collect(Collectors.toList());
    }

}

