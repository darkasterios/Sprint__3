import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterClient {

    private final static String COURIER_PATH = "api/v1/courier/";

    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .extract()
                .path("ok");
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

    public int createExistingCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .extract()
                .statusCode();

    }

    public boolean createCourierWithObligatoryFields(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    public boolean checkSuccessfulRequestBodyAfterCreating(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .extract()
                .path("ok");
    }

    public int checkStatusCodeAfterCreating(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .extract()
                .statusCode();
    }


    public String createWithoutPassword(CourierWithoutPassword courierWithoutPassword) {
        return given()
                .spec(getBaseSpec())
                .body(courierWithoutPassword)
                .when()
                .post(COURIER_PATH)
                .then()
                .extract()
                .path("message");
    }

    public String creatingIdenticalLoginsCouriers(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .extract()
                .path("message");

    }

    public String loginWithWrongPassword(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    public String loginWithoutPasswordField(CourierWithoutPassword courierWithoutPassword) {
        return given()
                .spec(getBaseSpec())
                .body(courierWithoutPassword)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .extract()
                .path("message");
    }

    public int logInAnGetStatusCode(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .extract()
                .statusCode();
    }


}