package com.example.order_service_foursales_system.modules.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopUserPurchaseDTO {

    private UUID customerId;
    private Long orderCount;


}
