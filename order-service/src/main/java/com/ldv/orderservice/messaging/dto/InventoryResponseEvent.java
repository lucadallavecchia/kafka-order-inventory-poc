package com.ldv.orderservice.messaging.dto;

import java.util.UUID;

public record InventoryResponseEvent(
        UUID orderId,
        String status,
        String message
) {}
