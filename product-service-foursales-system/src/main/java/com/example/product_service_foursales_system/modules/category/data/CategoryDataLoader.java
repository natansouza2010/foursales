package com.example.product_service_foursales_system.modules.category.data;

import com.example.product_service_foursales_system.modules.category.model.Category;
import com.example.product_service_foursales_system.modules.category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryDataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
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
}
