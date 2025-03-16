package com.example.product_service_foursales_system.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Description cannot be empty")
    private String description;
}