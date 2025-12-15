package com.ldv.orderservice.repo;

import com.ldv.orderservice.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryOrderRepository {

    private final Map<UUID, Order> store = new ConcurrentHashMap<>();

    public Order save(Order order) {
        store.put(order.id(), order);
        return order;
    }

    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }
}
