package ru.lanit.service.order;

import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.Order;
import ru.lanit.model.entity.User;

import java.util.List;

public interface OrderService {
    void save(Order order);
    List<Order> findAll();
    List<Order> findAllByUser(User user);
}
