package ru.lanit.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.lanit.model.criteria.ItemPage;
import ru.lanit.model.criteria.ItemSearchCriteria;
import ru.lanit.model.entity.Item;
import ru.lanit.repository.ItemRepository;
import ru.lanit.repository.criteria.ItemCriteriaRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    public void saveAll(List<Item> items) {
        repository.saveAll(items);
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Item> findAllItemsBySubCategory(String parentCategory, String subCategoryName) {
        return repository.findAll()
                .stream()
                .filter(item -> item.getCategory().getName().equals(subCategoryName))
                .filter(item -> item.getCategory().getParentCategory().getName().equals(parentCategory))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Item> getItems(ItemPage itemPage, ItemSearchCriteria itemSearchCriteria) {
        return criteriaRepository.findAllWihFilters(itemPage, itemSearchCriteria);
    }
}
