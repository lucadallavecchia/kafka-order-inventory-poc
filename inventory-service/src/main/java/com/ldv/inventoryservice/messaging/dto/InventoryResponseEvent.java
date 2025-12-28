package com.ldv.inventoryservice.messaging.dto;

import java.util.UUID;

public record InventoryResponseEvent(
        UUID orderId,
        String status, // "SUCCESS" o "FAILURE"
        String message
) {}
