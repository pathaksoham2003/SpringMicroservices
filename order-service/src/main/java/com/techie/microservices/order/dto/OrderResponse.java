package com.techie.microservices.order.dto;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public record OrderResponse(Long id,
                           String orderNumber,
                           String skuCode,
                           BigDecimal price,
                           Integer quantity) {
}
