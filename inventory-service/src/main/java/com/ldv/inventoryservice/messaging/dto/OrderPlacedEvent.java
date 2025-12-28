package com.ldv.inventoryservice.messaging.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderPlacedEvent(
        UUID orderId,
        String customerId,
        Instant createdAt,
        List<OrderItemEvent> items
) {}
