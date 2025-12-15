package com.ldv.orderservice.api.order;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetOrderResponse(
        UUID orderId,
        String customerId,
        List<Item> items,
        String status,
        Instant createdAt
) {
    public record Item(
            String sku,
            int quantity
    ) {}
}
