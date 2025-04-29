package com.techie.microservices.product.controller;

import com.techie.microservices.product.dto.DBSResponse;
import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.model.Product;
import com.techie.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DBSResponse<List<ProductResponse>> getProducts() {
        DBSResponse<List<ProductResponse>> dbsResponse = new DBSResponse<>();
        dbsResponse.setData(productService.getProducts());
        dbsResponse.setMessage("Got the products successfully");
        return dbsResponse;
    }
}
