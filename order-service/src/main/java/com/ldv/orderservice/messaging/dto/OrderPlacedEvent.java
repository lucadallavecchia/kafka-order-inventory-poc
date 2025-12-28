package com.ldv.orderservice.messaging.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderPlacedEvent(
        UUID orderId,
        String customerId,
        Instant createdAt,
        List<OrderItemEvent> items
) {}