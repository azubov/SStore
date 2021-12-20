package ru.lanit.service.cart;

import ru.lanit.model.entity.Cart;
import ru.lanit.model.entity.User;

public interface CartService {
    Cart getCart(User user);
    void save(Cart cart);
}
