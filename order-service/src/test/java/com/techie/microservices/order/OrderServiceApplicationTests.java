package com.techie.microservices.order;

import com.techie.microservices.order.stubs.InventoryStubClient;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port=0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer localPort;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = localPort;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder() {
		String submitOrderJson = """
				{
				"skuCode":"iphone_15",
				"price":1000,
				"quantity":1
				}
				""";
		InventoryStubClient.stubInventory("iphone_15",1);
		var responseBodyString = RestAssured.given().contentType("application/json")
				.body(submitOrderJson)
				.when()
				.post("/api/order")
				.then()
				.log()
				.all()
				.statusCode(201)
				.extract()
				.body()
				.asString();
		JsonPath jsonPath = new JsonPath(responseBodyString);
		assertThat(jsonPath.getString("message"), Matchers.is("Order placed successfully"));
	}

	@Test
	void contextLoads() {
	}

}
