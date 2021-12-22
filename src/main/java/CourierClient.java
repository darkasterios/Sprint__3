import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterClient {

    private final static String COURIER_PATH = "api/v1/courier/";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public int loginAndGetId(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .extract()
                .path("id");
    }

    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    public ValidatableResponse create(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse createWithoutPassword(CourierWithoutPassword courierWithoutPassword) {
        return given()
                .spec(getBaseSpec())
                .body(courierWithoutPassword)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    public ValidatableResponse loginWithoutPasswordField(CourierWithoutPassword courierWithoutPassword) {
        return given()
                .spec(getBaseSpec())
                .body(courierWithoutPassword)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }
}