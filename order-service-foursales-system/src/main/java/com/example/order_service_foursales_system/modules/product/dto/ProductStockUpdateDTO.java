package com.example.order_service_foursales_system.modules.product.dto;

import com.example.order_service_foursales_system.modules.order.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockUpdateDTO {
    private UUID productId;
    private int quantity;


    public static ProductStockUpdateDTO of(OrderItem orderItem) {
        var response = new ProductStockUpdateDTO();
        BeanUtils.copyProperties(orderItem, response);
        return response;
    }
}
