package com.example.product_service_foursales_system.modules.product.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {



    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminProduct() {
        return "Teste do Admin!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String getUserProducts() {
        return "Teste do Usuário!";
    }
}
