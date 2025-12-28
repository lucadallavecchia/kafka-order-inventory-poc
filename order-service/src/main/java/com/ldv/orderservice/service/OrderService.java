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

}
