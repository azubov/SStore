package ru.lanit.model.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchCriteria {
    private Double priceLow;
    private Double priceHigh;
    private String color;
}
