package com.ldv.orderservice.service;


import com.ldv.orderservice.controller.dto.OrderRequest;
import com.ldv.orderservice.controller.dto.OrderResponse;
import com.ldv.orderservice.entity.Order;
import com.ldv.orderservice.mapper.OrderMapper;
import com.ldv.orderservice.messaging.OrderProducer;
import com.ldv.orderservice.messaging.dto.OrderPlacedEvent;
import com.ldv.orderservice.model.OrderStatus;
import com.ldv.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderProducer orderProducer;
    private final OrderMapper mapper;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        Order order = mapper.toEntity(request);
        order.setStatus(OrderStatus.PENDING); // default status is PENDING

        // save in db
        Order savedOrder = repository.save(order);

        // send message
        OrderPlacedEvent event = mapper.toEvent(savedOrder);
        orderProducer.sendOrderEvent(event);

        return mapper.toResponse(savedOrder);
    }

    @Transactional
    public void updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        order.setStatus(newStatus);

        repository.save(order);

        log.info("Order {} successfully updated to {} in Database", orderId, newStatus);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

}
