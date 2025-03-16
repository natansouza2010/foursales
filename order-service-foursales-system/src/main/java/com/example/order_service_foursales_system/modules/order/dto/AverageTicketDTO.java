package com.example.order_service_foursales_system.modules.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AverageTicketDTO {
    private UUID customerId;
    private Double avgTicket;
}
