package com.ldv.orderservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(

        @NotBlank(message = "Customer ID is mandatory")
        String customerId,

        @NotEmpty(message = "Order items cannot be empty")
        List<OrderItemDto> items
) {
}
