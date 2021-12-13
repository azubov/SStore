package ru.lanit.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.model.dto.Cart;
import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.Order;
import ru.lanit.model.entity.User;
import ru.lanit.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Order order) {
        repository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order createOrderFromCart() {
        List<Item> itemsFromCart = new ArrayList<>();

        for (Map.Entry<Item, Integer> entry : Cart.getAll().entrySet()) {
            for (int i = entry.getValue(); i > 0; i--) {
                itemsFromCart.add(entry.getKey());
            }
        }

        Order order = new Order();
        order.setItems(itemsFromCart);
        return order;
    }

    @Override
    public List<Order> findAllByUser(User user) {
        return repository.findAll()
                .stream()
                .filter(order -> order.getUser().equals(user))
                .collect(Collectors.toList());
    }
}
