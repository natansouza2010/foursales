package com.example.order_service_foursales_system.modules.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private UUID customerId;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;
    private String status;
}
