package com.techie.microservices.order.service;

import com.techie.microservices.order.client.InventoryClient;
import com.techie.microservices.order.dto.OrderRequest;
import com.techie.microservices.order.dto.OrderResponse;
import com.techie.microservices.order.event.OrderPlacedEvent;
import com.techie.microservices.order.model.Order;
import com.techie.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    //    1. Mockito :- This just mocks the response data of the api call.
//    2. WireMock :- This is used to construct the actual api http call for the testing environment. (We make api call to wiremock which makes api call to inventory service)
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),
                orderRequest.quantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID()
                    .toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());
//        BeanUtils.copyProperties(orderRequest,order);


            // Send Email
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            System.out.println(orderRequest.userDetails());
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
            log.info("Start - Sending order placed event {} to Kafka topic order-placed",orderPlacedEvent);
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            log.info("End - Sending order placed event {} to Kafka topic order-placed",orderPlacedEvent);

            Order savedOrder = orderRepository.save(order);
            return new OrderResponse(savedOrder.getId(),
                    savedOrder.getOrderNumber(),
                    savedOrder.getSkuCode(),
                    savedOrder.getPrice(),
                    savedOrder.getQuantity());
        } else {
            throw new RuntimeException("Product with SkuCode "
                    + orderRequest.skuCode() + " is not in stock");
        }
    }
}
