package com.example.order_service_foursales_system.modules.product.client;

import com.example.order_service_foursales_system.modules.order.dto.OrderItemRequest;
import com.example.order_service_foursales_system.modules.product.dto.ProductStockAndPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "${product.services.url}")
public interface ProductServiceFeignClient {

    @PostMapping("/check-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    List<ProductStockAndPriceResponse> checkStockAndCalculate(@RequestBody List<OrderItemRequest> items);

}

