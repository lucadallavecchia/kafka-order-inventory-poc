package com.ldv.orderservice.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        String productId,
        Integer quantity,
        BigDecimal price
) {
}
