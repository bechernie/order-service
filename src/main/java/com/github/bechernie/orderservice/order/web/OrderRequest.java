package com.github.bechernie.orderservice.order.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotBlank(message = "{isbn.defined}")
        String isbn,
        @NotNull(message = "{quantity.defined}")
        @Min(value = 1, message = "{quantity.min-item}")
        @Max(value = 5, message = "{quantity.max-item}")
        Integer quantity
) {
}
