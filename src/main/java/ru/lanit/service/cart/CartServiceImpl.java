package ru.lanit.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.model.entity.Cart;
import ru.lanit.model.entity.User;
import ru.lanit.repository.CartRepository;

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
}
