package com.ldv.inventoryservice.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record InventoryRequest(

        @NotNull(message = "Product ID is required")
        UUID productId,

        @NotNull(message = "Quantity is required")
        @PositiveOrZero(message = "Quantity must be zero or positive")
        Integer quantity
) {}
