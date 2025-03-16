package com.example.product_service_foursales_system.modules.category.service;

import com.example.product_service_foursales_system.config.exception.ValidationException;
import com.example.product_service_foursales_system.modules.category.dto.CategoryRequest;
import com.example.product_service_foursales_system.modules.category.dto.CategoryResponse;
import com.example.product_service_foursales_system.modules.category.model.Category;
import com.example.product_service_foursales_system.modules.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Integer id){
        if(isEmpty(id)){
            throw new ValidationException("The category id was not informed");
        }
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no category for the given ID."));
    }



    public CategoryResponse save(CategoryRequest categoryRequest){
        var category = categoryRepository.save(Category.of(categoryRequest));
        return CategoryResponse.of(category);
    }



    public List<CategoryResponse> findAll(){
        return categoryRepository.findAll().stream().map(category -> CategoryResponse.of(category)).collect(Collectors.toList());
    }

    public CategoryResponse findByIdResponse(Integer id){
        return CategoryResponse.of(findById(id));
    }










}
