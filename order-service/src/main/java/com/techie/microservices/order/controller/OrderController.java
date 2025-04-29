package com.techie.microservices.order.controller;

import com.techie.microservices.order.dto.DBSResponse;
import com.techie.microservices.order.dto.OrderRequest;
import com.techie.microservices.order.dto.OrderResponse;
import com.techie.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<DBSResponse<OrderResponse>> createOrder(
            @RequestBody OrderRequest orderRequest) {
        DBSResponse<OrderResponse> dbsResponse = new DBSResponse<>();
        try {
            OrderResponse orderResponse = orderService.placeOrder(orderRequest);
            dbsResponse.setData(orderResponse);
            dbsResponse.setMessage("Order placed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(dbsResponse);
        }catch (Exception ex) {
            ex.printStackTrace();
            dbsResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(dbsResponse);
        }
    }
}
