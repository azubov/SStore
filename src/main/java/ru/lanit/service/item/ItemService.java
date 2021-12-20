package ru.lanit.service.item;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import ru.lanit.model.criteria.ItemPage;
import ru.lanit.model.criteria.ItemSearchCriteria;
import ru.lanit.model.dto.ItemDto;
import ru.lanit.model.entity.Item;

import java.util.List;

public interface ItemService {

    void save(Item item);
    void saveAll(List<Item> items);
    Item findById(Long id);
    List<Item> findAll();
    List<Item> findAllItemsBySubCategory(String parentCategory, String subCategoryName);
    Page<Item> getItems(ItemPage itemPage, ItemSearchCriteria itemSearchCriteria);
    void deleteById(Long id);

    void bindItem(Model model, ItemDto itemDto, List<String> categoryList);
}
