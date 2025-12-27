package com.ldv.orderservice.controller;

import com.ldv.orderservice.dto.OrderRequest;
import com.ldv.orderservice.entity.Order;
import com.ldv.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request){
        Order savedOrder = orderService.placeOrder(request);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

}
