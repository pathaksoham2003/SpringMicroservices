package com.techie.microservices.order.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {
    @GetExchange(url = "/api/inventory")
    boolean isInStock(@RequestParam String skuCode,
                      @RequestParam Integer quantity);
}
