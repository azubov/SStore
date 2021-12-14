package ru.lanit.model.dto;

import ru.lanit.model.entity.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private static Map<Item, Integer> items = new HashMap<>();

    public static void add(Item item, Integer quantity) {
        if (quantity == 0) {
            items.remove(item);
        } else {
            items.put(item, quantity);
        }
    }

    public static Map<Item, Integer> getAll() {
        return items;
    }

    public static void clear() {
        items.clear();
    }

    public static List<Item> getItemsFromCart() {
        List<Item> itemsFromCart = new ArrayList<>();

        for (Map.Entry<Item, Integer> entry : Cart.getAll().entrySet()) {
            for (int i = entry.getValue(); i > 0; i--) {
                itemsFromCart.add(entry.getKey());
            }
        }

        return itemsFromCart;
    }
}
