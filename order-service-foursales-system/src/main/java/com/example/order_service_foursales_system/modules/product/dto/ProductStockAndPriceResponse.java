package com.example.order_service_foursales_system.modules.product.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductStockAndPriceResponse {

    private UUID productId;
    private Integer quantityAvailable;
    private BigDecimal price;


}
