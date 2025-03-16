package com.example.order_service_foursales_system.modules.order.repository;

import com.example.order_service_foursales_system.modules.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}