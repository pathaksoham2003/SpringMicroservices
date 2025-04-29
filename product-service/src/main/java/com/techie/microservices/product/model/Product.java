package com.techie.microservices.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;
    private String skuCode;
    private String name;
    private String description;
    private BigDecimal price;
}
