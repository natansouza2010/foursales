package com.example.product_service_foursales_system.modules.product.controller;

import com.example.product_service_foursales_system.config.exception.SuccessResponse;
import com.example.product_service_foursales_system.modules.product.dto.OrderItemRequest;
import com.example.product_service_foursales_system.modules.product.dto.ProductRequest;
import com.example.product_service_foursales_system.modules.product.dto.ProductResponse;
import com.example.product_service_foursales_system.modules.product.dto.ProductStockAndPriceResponse;
import com.example.product_service_foursales_system.modules.product.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse save(@RequestBody ProductRequest request){
        return productService.save(request);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ProductResponse> findAll(){
        return productService.findAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ProductResponse findById(@PathVariable UUID id){
        return productService.findByIdResponse(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse update (@RequestBody ProductRequest request ,@PathVariable UUID id){
        return productService.update(request, id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SuccessResponse delete (@PathVariable UUID id){
        return productService.delete(id);
    }


    @PostMapping("/check-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ProductStockAndPriceResponse> checkStockAndCalculate(@RequestBody List<OrderItemRequest> items) {
        return productService.checkStock(items);
    }


}
