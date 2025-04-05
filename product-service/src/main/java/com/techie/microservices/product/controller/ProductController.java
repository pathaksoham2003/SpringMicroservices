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
    public ResponseEntity<DBSResponse<Product>> createProduct(@RequestBody ProductRequest productRequest) {
        DBSResponse<Product> dbsResponse = new DBSResponse<Product>();
        try {
            Product product = productService.createProduct(productRequest);
            dbsResponse.setData(product);
            dbsResponse.setMessage("Product created successfully");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(dbsResponse);
        } catch (Exception e) {
            dbsResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(dbsResponse);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DBSResponse<List<ProductResponse>>> getProducts() {
        DBSResponse<List<ProductResponse>> dbsResponse = new DBSResponse<List<ProductResponse>>();
        try {
            List<ProductResponse> product = productService.getProducts();
            dbsResponse.setData(product);
            dbsResponse.setMessage("Fetched all products successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(dbsResponse);
        } catch (Exception e) {
            dbsResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(dbsResponse);
        }
    }
}
