package com.ldv.orderservice.messaging.dto;

import java.util.UUID;

public record OrderItemEvent(
        UUID productId,
        Integer quantity
) {}
