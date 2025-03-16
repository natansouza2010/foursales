package com.example.product_service_foursales_system.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockAndPriceResponse {
    private UUID productId;
    private BigDecimal price;

}