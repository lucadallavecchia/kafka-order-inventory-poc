package com.ldv.orderservice.service;

import com.ldv.orderservice.api.order.CreateOrderRequest;
import com.ldv.orderservice.domain.Order;
import com.ldv.orderservice.domain.OrderItem;
import com.ldv.orderservice.domain.OrderStatus;
import com.ldv.orderservice.repo.InMemoryOrderRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final InMemoryOrderRepository repository;

    public OrderService(InMemoryOrderRepository repository) {
        this.repository = repository;
    }

    public Order create(CreateOrderRequest request) {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        List<OrderItem> items = request.items().stream()
                .map(i -> new OrderItem(i.sku(), i.quantity()))
                .toList();

        Order order = new Order(id, request.customerId(), items, now, OrderStatus.CREATED);
        return repository.save(order);
    }

    public Optional<Order> findById(UUID orderId) {
        return repository.findById(orderId);
    }
}
