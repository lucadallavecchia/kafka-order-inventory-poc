package com.ldv.inventoryservice.messaging.dto;

import java.util.UUID;

public record OrderItemEvent(
        UUID productId,
        Integer quantity
) {}
