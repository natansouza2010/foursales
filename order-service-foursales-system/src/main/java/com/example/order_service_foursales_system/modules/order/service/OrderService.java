package com.example.order_service_foursales_system.modules.order.service;

import com.example.order_service_foursales_system.config.exception.ValidationException;
import com.example.order_service_foursales_system.modules.order.dto.*;
import com.example.order_service_foursales_system.modules.order.enums.OrderStatus;
import com.example.order_service_foursales_system.modules.order.model.Order;
import com.example.order_service_foursales_system.modules.order.model.OrderItem;
import com.example.order_service_foursales_system.modules.order.repository.OrderItemRepository;
import com.example.order_service_foursales_system.modules.order.repository.OrderRepository;
import com.example.order_service_foursales_system.modules.product.client.ProductServiceFeignClient;
import com.example.order_service_foursales_system.modules.product.dto.ProductStockAndPriceResponse;
import com.example.order_service_foursales_system.modules.product.dto.ProductStockUpdateDTO;
import com.example.order_service_foursales_system.modules.product.rabbitmq.StockUpdateSender;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductServiceFeignClient productServiceFeignClient;
    private final StockUpdateSender stockUpdateSender;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductServiceFeignClient productServiceFeignClient, StockUpdateSender stockUpdateSender) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productServiceFeignClient = productServiceFeignClient;
        this.stockUpdateSender = stockUpdateSender;
    }


    @Transactional
    public void createOrder(OrderDataRequest orderDataRequest) {
        var productsRequest = orderDataRequest.getItems();

        var stockAndPricesResponse = checkStock(productsRequest);

        BigDecimal totalPrice = calculateTotalPrice(stockAndPricesResponse);
        Order order = createInitialOrder(orderDataRequest, totalPrice);

        createItemOrder(order, orderDataRequest, stockAndPricesResponse);

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());
    }


    private List<ProductStockAndPriceResponse> checkStock(List<OrderItemRequest> productsRequest) {
        List<ProductStockAndPriceResponse> stockAndPricesResponse;
        try {
            stockAndPricesResponse = productServiceFeignClient.checkStockAndCalculate(productsRequest);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
        return stockAndPricesResponse;
    }


    private void createItemOrder(Order order, OrderDataRequest orderDataRequest, List<ProductStockAndPriceResponse> stockAndPricesResponse) {
        List<OrderItem> orderItems = orderDataRequest.getItems().stream()
                .map(item -> {
                    ProductStockAndPriceResponse product = stockAndPricesResponse.stream()
                            .filter(p -> p.getProductId().equals(item.getProductId()))
                            .findFirst()
                            .orElseThrow(() -> new ValidationException("Product not found"));

                    OrderItem orderItem = new OrderItem(null, item.getProductId(), item.getQuantity(), order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
    }


    public void processPayment(UUID orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ValidationException("Order not found"));
        checkStatusPending(order);
        var productsRequest = order.getOrderItems();
        try{
            productServiceFeignClient.checkStockAndCalculate(convertOrderItemsToRequest(productsRequest));
        }catch (FeignException e){
            cancelOrder(order);
            throw new ValidationException(e.getMessage());
        }
        order.setStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
        sendProductStockUpdateMessage(order.getOrderItems());



    }

    private void checkStatusPending(Order order){
        if(order.getStatus() != OrderStatus.PENDING){
            throw new ValidationException("Order status is not PENDING");
        }
    }

    private void cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }


    private void sendProductStockUpdateMessage(List<OrderItem> productsRequest) {

        List<ProductStockUpdateDTO> productStockUpdateDTOs = productsRequest.stream()
                .map(ProductStockUpdateDTO::of) //
                .collect(Collectors.toList());

        stockUpdateSender.sendProductStockUpdateMessage(productStockUpdateDTOs);
    }

    public List<OrderItemRequest> convertOrderItemsToRequest(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> new OrderItemRequest(
                        orderItem.getProductId(),
                        orderItem.getQuantity()
                ))
                .collect(Collectors.toList());
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


    public List<TopUserPurchaseDTO> getTop5UsersWithMostOrders() {
        return orderRepository.findTop5UsersWithMostOrders()
                .stream()
                .map(result -> new TopUserPurchaseDTO(
                        (UUID) result[0],
                        ((Number) result[1]).longValue()))
                .collect(Collectors.toList());
    }


    public List<AverageTicketDTO> getAverageTicketPerUser() {
        return orderRepository.findAverageTicketPerUser()
                .stream()
                .map(result -> new AverageTicketDTO(
                        (UUID) result[0],
                        (Double) result[1]))
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalRevenueForCurrentMonth() {
        return orderRepository.findTotalRevenueForCurrentMonth();
    }


    public List<OrderDTO> getOrdersByCustomerId(UUID customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        return orders.stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalPrice(),
                        order.getOrderDate(),
                        order.getStatus().name()))  // Convertendo o enum Status para string
                .collect(Collectors.toList());
    }
}
