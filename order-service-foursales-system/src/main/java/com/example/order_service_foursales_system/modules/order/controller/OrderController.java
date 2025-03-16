package com.example.order_service_foursales_system.modules.order.controller;


import com.example.order_service_foursales_system.config.exception.SuccessResponse;
import com.example.order_service_foursales_system.modules.order.dto.OrderDataRequest;
import com.example.order_service_foursales_system.modules.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public SuccessResponse createOrder(@RequestBody OrderDataRequest orderDataRequest) {
        orderService.createOrder(orderDataRequest);

        return SuccessResponse.create("Order created with success");

    }

    @PostMapping("/{orderId}/process-payment")
    public void paymentOrder(@PathVariable UUID orderId) {
        orderService.processPayment(orderId);
    }


}
