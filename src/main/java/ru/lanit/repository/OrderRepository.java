package ru.lanit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lanit.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
