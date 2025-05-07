package org.mch;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class InventoryResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/inventory")
          .then()
             .statusCode(200);
    }

}