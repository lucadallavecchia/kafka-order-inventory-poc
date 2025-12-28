package com.ldv.orderservice.dto;

import com.ldv.orderservice.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customerId,
        OrderStatus status,
        List<OrderItemDto> items
) {
}
