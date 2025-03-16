package com.example.product_service_foursales_system.modules.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @JsonProperty("quantity_available")
    @NotNull(message = "Quantity available cannot be null")
    @Min(value = 0, message = "Quantity available cannot be negative")
    private Integer quantityAvailable;

    @NotNull(message = "Category ID cannot be null")
    private Integer categoryId;

    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;
}
