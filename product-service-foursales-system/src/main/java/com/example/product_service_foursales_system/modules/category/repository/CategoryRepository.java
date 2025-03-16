package com.example.product_service_foursales_system.modules.category.repository;

import com.example.product_service_foursales_system.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
