package ru.lanit.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.lanit.model.criteria.ItemPage;
import ru.lanit.model.criteria.ItemSearchCriteria;
import ru.lanit.model.dto.Color;
import ru.lanit.model.dto.ImageSet;
import ru.lanit.model.dto.ItemDto;
import ru.lanit.model.entity.Category;
import ru.lanit.model.entity.Item;
import ru.lanit.repository.ItemRepository;
import ru.lanit.repository.criteria.ItemCriteriaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final ItemCriteriaRepository criteriaRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository repository, ItemCriteriaRepository criteriaRepository) {
        this.repository = repository;
        this.criteriaRepository = criteriaRepository;
    }

    @Override
    public void save(Item item) {
        repository.save(item);
    }

    @Override
    public void saveAll(List<Item> items) {
        repository.saveAll(items);
    }

    @Override
    public Item findById(Long id) {
        Optional<Item> item = repository.findById(id);
        return item.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Item> findAllItemsBySubCategory(String parentCategory, String subCategoryName) {
        return repository.findAllItemsBySubCategory(parentCategory, subCategoryName);
    }

    @Override
    public Page<Item> getItems(ItemPage itemPage, ItemSearchCriteria itemSearchCriteria) {
        return criteriaRepository.findAllWihFilters(itemPage, itemSearchCriteria);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void bindItem(Model model, ItemDto itemDto, List<String> categoryList) {
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("colors", Color.values());
        model.addAttribute("imageSet", ImageSet.getImages());
        model.addAttribute("itemDto", itemDto);
    }

    @Override
    public Item createItemFrom(ItemDto itemDto, Category category) {
        Item item = new Item();
        item.populateWith(itemDto, category);
        return item;
    }

    @Override
    public void bindDto(ItemDto itemDto, Long id) {
        Item itemFromDb = findById(id);
        itemDto.populateWith(itemFromDb);
    }

    @Override
    public Item findItemAndPopulateWith(Long id, ItemDto itemDto, Category category) {
        Item item = findById(id);
        item.populateWith(itemDto, category);
        return item;
    }

    @Override
    public void bindDtoWithImage(ItemDto itemDto, String imageName) {
        itemDto.setUploadedImageName(imageName);
    }
}
