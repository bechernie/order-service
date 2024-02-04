package com.polarbookshop.orderservice.order.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotBlank(message = "error.orders.isbn.missing") String isbn,

        @NotNull(message = "error.orders.quantity.missing")
        @Min(value = 1, message = "error.orders.quantity.min")
        @Max(value = 5, message = "error.orders.quantity.max")
        Integer quantity
) {
}
