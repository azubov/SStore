package ru.lanit.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.model.entity.Cart;
import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.User;
import ru.lanit.repository.CartRepository;

import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository repository;

    @Autowired
    public CartServiceImpl(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cart getCart(User user) {
        if (!repository.existsCartByUser(user)) {
            Cart cart = new Cart(user);
            repository.save(cart);
        }
        return repository.getCartByUser(user);
    }

    @Override
    public void save(Cart cart) {
        repository.save(cart);
    }

    @Override
    public void add(Cart cart, Item item, int quantity) {
        cart.add(item, quantity);
    }

    @Override
    public void clear(Cart cart) {
        cart.clear();
    }

    @Override
    public Map<Item, Integer> getItemsMap(Cart cart) {
        return cart.getAll();
    }
}
