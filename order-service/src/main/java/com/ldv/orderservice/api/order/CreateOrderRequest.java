package com.ldv.orderservice.api.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank String customerId,
        @NotEmpty @Valid List<Item> items
) {
    public record Item(
            @NotBlank String sku,
            @NotNull @Positive Integer quantity
    ) {}
}
