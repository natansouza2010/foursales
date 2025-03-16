package com.example.product_service_foursales_system.modules.data;

import com.example.product_service_foursales_system.modules.category.model.Category;
import com.example.product_service_foursales_system.modules.category.repository.CategoryRepository;
import com.example.product_service_foursales_system.modules.product.model.Product;
import com.example.product_service_foursales_system.modules.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadProducts();
    }

    private void loadCategories() {
        if (categoryRepository.count() == 0) {
            List<Category> categories = List.of(
                    new Category(null, "Electronics"),
                    new Category(null, "Clothing"),
                    new Category(null, "Books")
            );
            categoryRepository.saveAll(categories);
            System.out.println("✅ Default categories inserted!");
        } else {
            System.out.println("ℹ️ Categories already exist. Skipping initialization.");
        }
    }

    private void loadProducts() {
        if (productRepository.count() == 0) {
            List<Category> categories = categoryRepository.findAll();

            Map<String, Category> categoryMap = categories.stream()
                    .collect(Collectors.toMap(Category::getDescription, c -> c));

            List<Product> products = List.of(
                    new Product(null, "Smartphone", "Latest model", new BigDecimal("800.00"),
                            categoryMap.get("Electronics"), 10, LocalDateTime.now(), LocalDateTime.now()),

                    new Product(null, "Laptop", "High performance", new BigDecimal("1500.00"),
                            categoryMap.get("Electronics"), 5, LocalDateTime.now(), LocalDateTime.now()),

                    new Product(null, "T-Shirt", "Cotton, Size M", new BigDecimal("20.00"),
                            categoryMap.get("Clothing"), 50, LocalDateTime.now(), LocalDateTime.now()),

                    new Product(null, "Java Book", "Programming book", new BigDecimal("35.00"),
                            categoryMap.get("Books"), 30, LocalDateTime.now(), LocalDateTime.now())
            );

            productRepository.saveAll(products);
            System.out.println("✅ Default products inserted!");
        } else {
            System.out.println("ℹ️ Products already exist. Skipping initialization.");
        }
    }
}
