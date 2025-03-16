package com.example.product_service_foursales_system.modules.category.model;

import com.example.product_service_foursales_system.modules.category.dto.CategoryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false)
    private String description;

    public static Category of(CategoryRequest categoryRequest){
        var category = new Category();
        BeanUtils.copyProperties(categoryRequest, category);
        return category;
    }


}