package com.ldv.orderservice.service;


import com.ldv.orderservice.dto.OrderRequest;
import com.ldv.orderservice.entity.Order;
import com.ldv.orderservice.mapper.OrderMapper;
import com.ldv.orderservice.model.OrderStatus;
import com.ldv.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        Order order = mapper.toEntity(request);
        order.setStatus(OrderStatus.PENDING); // default status is PENDING
        return repository.save(order);
    }

}
