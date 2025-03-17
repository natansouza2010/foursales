package com.example.order_service_foursales_system.modules.order.repository;

import com.example.order_service_foursales_system.modules.order.dto.AverageTicketDTO;
import com.example.order_service_foursales_system.modules.order.dto.TopUserPurchaseDTO;
import com.example.order_service_foursales_system.modules.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o.customerId, COUNT(o.id) " +
            "FROM Order o " +
            "WHERE o.status = 'APPROVED' " +
            "GROUP BY o.customerId " +
            "ORDER BY COUNT(o.id) DESC")
    List<Object[]> findTop5UsersWithMostOrders();

    @Query("SELECT o.customerId, AVG(o.totalPrice) " +
            "FROM Order o " +
            "GROUP BY o.customerId")
    List<Object[]> findAverageTicketPerUser();


    @Query("SELECT SUM(o.totalPrice) " +
            "FROM Order o " +
            "WHERE YEAR(o.orderDate) = YEAR(CURRENT_DATE) " +
            "AND MONTH(o.orderDate) = MONTH(CURRENT_DATE)")
    BigDecimal findTotalRevenueForCurrentMonth();

    List<Order> findByCustomerId(UUID customerId);
}