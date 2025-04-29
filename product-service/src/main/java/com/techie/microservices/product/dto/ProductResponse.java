package com.techie.microservices.product.dto;

import lombok.*;

import java.math.BigDecimal;

public record ProductResponse(String id, String name,String skuCode, String description , BigDecimal price) {
}
