package ru.lanit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lanit.model.entity.Cart;
import ru.lanit.model.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsCartByUser(User user);
    Cart getCartByUser(User user);
}
