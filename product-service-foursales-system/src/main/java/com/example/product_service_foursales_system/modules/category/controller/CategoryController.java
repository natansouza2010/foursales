package com.example.product_service_foursales_system.modules.category.controller;

import com.example.product_service_foursales_system.modules.category.dto.CategoryRequest;
import com.example.product_service_foursales_system.modules.category.dto.CategoryResponse;
import com.example.product_service_foursales_system.modules.category.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse findById(@PathVariable Integer id){
        return categoryService.findByIdResponse(id);
    }






}