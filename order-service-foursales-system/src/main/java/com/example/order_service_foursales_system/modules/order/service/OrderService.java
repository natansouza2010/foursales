package com.example.order_service_foursales_system.modules.order.service;

import com.example.order_service_foursales_system.config.exception.ValidationException;
import com.example.order_service_foursales_system.modules.order.dto.OrderDataRequest;
import com.example.order_service_foursales_system.modules.order.dto.OrderItemRequest;
import com.example.order_service_foursales_system.modules.order.enums.OrderStatus;
import com.example.order_service_foursales_system.modules.order.model.Order;
import com.example.order_service_foursales_system.modules.order.model.OrderItem;
import com.example.order_service_foursales_system.modules.order.repository.OrderItemRepository;
import com.example.order_service_foursales_system.modules.order.repository.OrderRepository;
import com.example.order_service_foursales_system.modules.product.client.ProductServiceFeignClient;
import com.example.order_service_foursales_system.modules.product.dto.ProductStockAndPriceResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductServiceFeignClient productServiceFeignClient;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductServiceFeignClient productServiceFeignClient) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productServiceFeignClient = productServiceFeignClient;
    }


    @Transactional
    public void createOrder(OrderDataRequest orderDataRequest) {
       var productsRequest = orderDataRequest.getItems();

       var stockAndPricesResponse = productServiceFeignClient.checkStockAndCalculate(productsRequest);


        BigDecimal totalPrice = calculateTotalPrice(stockAndPricesResponse);
        Order order = createInitialOrder(orderDataRequest, totalPrice);
        createItemOrder(order ,orderDataRequest, stockAndPricesResponse);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());


    }


    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() != null) {
            return authentication.getCredentials().toString();
        }
        return null;
    }

    private void createItemOrder(Order order, OrderDataRequest orderDataRequest, List<ProductStockAndPriceResponse> stockAndPricesResponse) {
        List<OrderItem> orderItems = orderDataRequest.getItems().stream()
                .map(item -> {
                    ProductStockAndPriceResponse product = stockAndPricesResponse.stream()
                            .filter(p -> p.getProductId().equals(item.getProductId()))
                            .findFirst()
                            .orElseThrow(() -> new ValidationException("Product not found"));

                    // Cria o OrderItem e associa ao pedido
                    OrderItem orderItem = new OrderItem(null, item.getProductId(), item.getQuantity(), order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
    }



    private BigDecimal calculateTotalPrice(List<ProductStockAndPriceResponse> stockAndPricesResponse) {
        return stockAndPricesResponse.stream()
                .map(ProductStockAndPriceResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private Order createInitialOrder(OrderDataRequest orderDataRequest, BigDecimal totalPrice) {
        return Order.builder()
                .customerId(orderDataRequest.getCustomerId())
                .totalPrice(totalPrice)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();
    }
}
