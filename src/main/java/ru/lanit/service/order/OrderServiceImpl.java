package ru.lanit.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.model.entity.Order;
import ru.lanit.model.entity.User;
import ru.lanit.repository.OrderRepository;

import java.util.List;

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

    @Override
    public List<Order> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }
}
