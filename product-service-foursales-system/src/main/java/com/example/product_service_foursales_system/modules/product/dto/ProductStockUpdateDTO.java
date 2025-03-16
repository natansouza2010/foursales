package com.example.product_service_foursales_system.modules.product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductStockUpdateDTO {
    private UUID productId;
    private int quantity;

}
