package com.techie.microservices.order.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryStubClient {
    public static void stubInventory(String skuCode,Integer quantity) {
        stubFor(
                get(urlEqualTo("/api/inventory?skuCode="+skuCode+"&quantity="+quantity))
                        .willReturn(
                                aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type","application/json")
                                        .withBody("true")));

    }
}
