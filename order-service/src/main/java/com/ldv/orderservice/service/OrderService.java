package com.ldv.orderservice.service;


import com.ldv.orderservice.dto.OrderRequest;
import com.ldv.orderservice.dto.OrderResponse;
import com.ldv.orderservice.entity.Order;
import com.ldv.orderservice.mapper.OrderMapper;
import com.ldv.orderservice.messaging.OrderProducer;
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

        Order savedOrder = repository.save(order);
        orderProducer.sendOrderEvent(savedOrder.getId().toString());

        return mapper.toResponse(savedOrder);
    }

}
