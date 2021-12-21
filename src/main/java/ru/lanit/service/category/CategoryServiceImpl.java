package ru.lanit.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.lanit.errors.ErrorType;
import ru.lanit.model.dto.CategoryDto;
import ru.lanit.model.dto.ImageSet;
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
        return repository.findAllSubCategories();
    }

    @Override
    public List<String> displaySubCategoryUniqueNames() {
        return findAllSubCategories().stream().map(Category::getName).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean existsCategoryByName(String name) {
        return repository.existsCategoryByName(name);
    }

    @Override
    public void bindCategory(Model model, CategoryDto categoryDto) {
        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("parentList", findAllParentCategories());
        model.addAttribute("categoryDto", categoryDto);
    }

    @Override
    public void bindWithError(Model model, CategoryDto categoryDto) {
        bindCategory(model, categoryDto);
        model.addAttribute("nameError", ErrorType.NAME_ALREADY_EXISTS.getDescription());
    }

    @Override
    public void populateParentCategory(CategoryDto categoryDto, Category category) {
        if (categoryDto.getParentName().isEmpty()) {
            category.populateWith(categoryDto, null);
        } else {
            Category parentCategory = findByName(categoryDto.getParentName());
            category.populateWith(categoryDto, parentCategory);
        }
    }
}

