package com.example.product_service_foursales_system.modules.product.rabbitmq;

import com.example.product_service_foursales_system.modules.product.dto.ProductStockUpdateDTO;
import com.example.product_service_foursales_system.modules.product.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockUpdateListener {

    private final ObjectMapper objectMapper;
    private final ProductService productService;

    public StockUpdateListener(ObjectMapper objectMapper, ProductService productService) {
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void onStockUpdate(String messageBody) {

        try {
            List<ProductStockUpdateDTO> productStockUpdateDTOList = objectMapper.readValue(messageBody, new TypeReference<List<ProductStockUpdateDTO>>() {});
            for (ProductStockUpdateDTO productStockUpdateDTO : productStockUpdateDTOList) {
                productService.updateProductStock(productStockUpdateDTO);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar a atualização do estoque: " + e.getMessage());
            e.printStackTrace();
        }

    }


}
