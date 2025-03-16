package com.example.order_service_foursales_system.modules.product.rabbitmq;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockUpdateSender {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "productExchange";
    private static final String ROUTING_KEY = "product.stock.update.routingKey";

    public StockUpdateSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProductStockUpdateMessage(Object message) {

        String jsonMessage = convertMessageToJson(message);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, jsonMessage);

    }

    private String convertMessageToJson(Object message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
