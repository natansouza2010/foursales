package com.example.order_service_foursales_system.modules.order.controller;


import com.example.order_service_foursales_system.config.exception.SuccessResponse;
import com.example.order_service_foursales_system.modules.order.dto.AverageTicketDTO;
import com.example.order_service_foursales_system.modules.order.dto.OrderDataRequest;
import com.example.order_service_foursales_system.modules.order.dto.TopUserPurchaseDTO;
import com.example.order_service_foursales_system.modules.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public SuccessResponse createOrder(@RequestBody OrderDataRequest orderDataRequest) {
        orderService.createOrder(orderDataRequest);

        return SuccessResponse.create("Order created with success");

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{orderId}/process-payment")
    public void paymentOrder(@PathVariable UUID orderId) {
        orderService.processPayment(orderId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-buyers")
    public List<TopUserPurchaseDTO> getTop5UsersWithMostOrders() {
        return orderService.getTop5UsersWithMostOrders();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/average-ticket")
    public List<AverageTicketDTO> getAverageTicketPerUser() {
        return orderService.getAverageTicketPerUser();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/monthly-revenue")
    public BigDecimal getTotalRevenueForCurrentMonth() {
        return orderService.getTotalRevenueForCurrentMonth();
    }



}
