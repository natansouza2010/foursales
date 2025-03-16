package com.example.product_service_foursales_system.modules.product.service;

import com.example.product_service_foursales_system.config.exception.SuccessResponse;
import com.example.product_service_foursales_system.config.exception.ValidationException;
import com.example.product_service_foursales_system.modules.category.service.CategoryService;
import com.example.product_service_foursales_system.modules.product.repository.ProductRepository;
import com.example.product_service_foursales_system.modules.product.dto.OrderItemRequest;
import com.example.product_service_foursales_system.modules.product.dto.ProductRequest;
import com.example.product_service_foursales_system.modules.product.dto.ProductResponse;
import com.example.product_service_foursales_system.modules.product.dto.ProductStockAndPriceResponse;
import com.example.product_service_foursales_system.modules.product.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public ProductResponse save (ProductRequest request){
        var category = categoryService.findById(request.getCategoryId());
        var product = productRepository.save(Product.of(request,  category));
        return ProductResponse.of(product);
    }


    public ProductResponse update(ProductRequest request, UUID id) {
        validateInformedId(id);
        var product = findById(id);
        var category = categoryService.findById(request.getCategoryId());

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantityAvailable(request.getQuantityAvailable());
        product.setCategory(category);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return ProductResponse.of(product);
    }



    public SuccessResponse delete(UUID id){
        validateInformedId(id);
        productRepository.deleteById(id);
        return SuccessResponse.create("The product was deleted");

    }

    public List<ProductResponse> findAll(){
        return productRepository.findAll().stream().map(product -> ProductResponse.of(product)).collect(Collectors.toList());
    }


    public ProductResponse findByIdResponse(UUID id) {
        return ProductResponse.of(findById(id));
    }

    public Product findById(UUID id) {
        validateInformedId(id);
        return productRepository.findById(id).orElseThrow( () -> new ValidationException("There's no product for the given ID."));
    }


    public List<ProductStockAndPriceResponse> checkStock(List<OrderItemRequest> itemsRequest) {
        return itemsRequest.stream()
                .map(item -> {
                    validateStock(item); // Valida se há estoque suficiente
                    var product = findById(item.getProductId()); // Busca o produto no banco
                    return new ProductStockAndPriceResponse(product.getId(), product.getPrice());
                })
                .collect(Collectors.toList());
    }


    private void validateStock(OrderItemRequest productQuantity){
        if(isEmpty(productQuantity.getProductId()) || isEmpty(productQuantity.getQuantity())){
            throw new ValidationException("Product ID and quantity must be informed");
        }
        var product = findById(productQuantity.getProductId());
        if(productQuantity.getQuantity() > product.getQuantityAvailable() ){
            throw new ValidationException(String.format("The product %s is out of stock.", product.getId()));


        }

    }



    private void validateInformedId(UUID id){
        if(isEmpty(id)){
            throw new ValidationException("The product ID must be informed.");
        }
    }

}
