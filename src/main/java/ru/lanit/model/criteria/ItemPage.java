package ru.lanit.model.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPage {
    private int pageNumber = 1;
    private int pageSize = 5;
    private int maxPageNumber = 1;
    private int[] itemsOnPage = {5,20,100};
}