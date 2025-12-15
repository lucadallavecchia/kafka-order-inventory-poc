package com.ldv.orderservice.api.order;

import java.time.Instant;
import java.util.UUID;

public record CreateOrderResponse(
        UUID orderId,
        String status,
        Instant createdAt
) {}
