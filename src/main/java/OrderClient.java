import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterClient {

    private static final String ORDER_PATH = "api/v1/orders/";

    public Response createOrder(Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH);
    }

    public ValidatableResponse getOrdersList(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }

}
