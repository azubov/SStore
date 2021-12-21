package ru.lanit.service.cart;

import ru.lanit.model.entity.Cart;
import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.User;

import java.util.Map;

public interface CartService {
    Cart getCart(User user);
    void save(Cart cart);

    void add(Cart cart, Item item, int quantity);
    void clear(Cart cart);
    Map<Item, Integer> getItemsMap(Cart cart);
}
