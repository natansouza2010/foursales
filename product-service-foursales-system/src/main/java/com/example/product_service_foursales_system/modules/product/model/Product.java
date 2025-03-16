package com.example.product_service_foursales_system.modules.product.model;

import com.example.product_service_foursales_system.modules.category.model.Category;
import com.example.product_service_foursales_system.modules.product.dto.ProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "fk_category", nullable = false)
    private Category category;
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }


    public static Product of(ProductRequest productRequest, Category category) {
        return Product
                .builder()
                .name(productRequest.getName())
                .quantityAvailable(productRequest.getQuantityAvailable())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .category(category)
                .updatedAt(LocalDateTime.now())
                .build();

    }

    public void updateStock(Integer quantity){
        this.quantityAvailable = this.quantityAvailable - quantity;
    }
}
