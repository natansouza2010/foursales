package com.example.order_service_foursales_system.modules.order.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDataRequest {
    private UUID customerId;
    private List<OrderItemRequest> items;

}