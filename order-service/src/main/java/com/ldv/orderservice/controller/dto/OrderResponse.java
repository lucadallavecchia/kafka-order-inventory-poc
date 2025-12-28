package com.ldv.orderservice.controller.dto;

import com.ldv.orderservice.model.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customerId,
        OrderStatus status,
        Instant createdAt,
        List<OrderItemDto> items
) {
}
