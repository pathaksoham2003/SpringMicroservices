package com.techie.microservices.product.service;

import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.model.Product;
import com.techie.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

//    The commented code below is not needed because of the constructor injection but can be replaced by RequiredArgsConstructor from Lombok
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public Product createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully with id: {}", product.getId());
        return product;
    }

    public List<ProductResponse> getProducts() {
        List<ProductResponse> products = productRepository.findAll().stream().map(product -> new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice())).toList();
        log.info("Fetched all products successfully");
        return products;
    }
}
