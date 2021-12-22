import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    CourierClient courierClient;
    private int courierId;


    @Before
    @Step("Creating a courier Client")
    public void setUp(){
        courierClient = new CourierClient();
    }

    @After
    @Step("Deleting courier after test")
    public void tearDown(){
        if(courierId!=0){
        courierClient.delete(courierId);}
    }

    @Test
    @DisplayName("Позитивный сценарий создания курьера.Проверка возвращаемого id курьера")
    @Description("Проверка успешного создания курьера, возвращаемый id не равен 0")
    @Step("Check the positive creating courier")
    public void courierCreateTest(){
        Courier courier = Courier.getRandom();

        boolean isCreated = courierClient.create(courier).assertThat().statusCode(201).extract().path("ok");
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

        assertTrue("Courier is not created",isCreated);
        assertThat("Courier id is incorrect", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Проверка невозможности создания 2-ух одинаковых курьеров.")
    @Description("Проверка статус кода при попытке создания 2-ух одинаковых курьеров")
    @Step("Check Status Code in negative creating 2 identical couriers")
    public void createIdenticalCouriersTest(){
        Courier courier = Courier.getRandom();

        courierClient.create(courier);
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));
        int actual = courierClient.create(courier).extract().statusCode();

        assertThat("In Identical couriers creating test status code is not 409",actual,equalTo(409));
    }

    @Test
    @DisplayName("Создание курьера с двумя обязательными полям логин и пароль")
    @Description("Проверка возможности создания курьера с передачей двух обязательных полей логин и пароль")
    @Step("Check courier creating with obligatory fields")
    public void createCourierWithObligatoryFieldsTest(){
     Courier courier = Courier.getRandom();

     boolean isCreated = courierClient.create(CourierCredentials.getCourierCredentials(courier))
             .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");;
     courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

     assertTrue("Courier is not created",isCreated);
    }

    @Test
    @DisplayName("Позитивный сценарий создания курьера. Тело ответа true")
    @Description("Проверка что при успешном создании курьера возвращаемое body true")
    @Step("Check that body is true in positive creating courier")
    public void checkSuccessfulRequestBodyCreateCourierTest(){
        Courier courier = Courier.getRandom();

        boolean actual = courierClient.create(courier).assertThat().statusCode(201).extract().path("ok");
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

        assertTrue("SuccessfulRequestBody returns false in positive courier creating test", actual);
    }

    @Test
    @DisplayName("Позитивный сценарий создания курьера. Проверка статус кода")
    @Description("Проверка что при успешном создании курьера, возвращаемый статус код = 201")
    @Step("Check that Status Code is 201 in positive creating courier")
    public void checkStatusCodePositiveCreatingCourierTest(){
        Courier courier = Courier.getRandom();

        int actual = courierClient.create(courier).assertThat().statusCode(201).extract().statusCode();
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

       assertThat("In positive courier creating test status code is not 201",actual,equalTo(201));
    }

    @Test
    @DisplayName("Негативный сценарий создания курьера. Создание курьера без пароля")
    @Description("Проверка сообщения об ошибке при создании курьера без пароля")
    @Step("Check error message in negative creating courier without password field")
    public void createCourierWithoutPasswordFieldTest(){
        Courier courier = Courier.getRandom();

        String actual = courierClient.createWithoutPassword(CourierWithoutPassword.getLoginCourier(courier))
                .extract()
                .path("message");

        assertThat("Error text for creating a courier without a password has been changed",actual,equalTo("Недостаточно данных для создания учетной записи"));
    }


    //Тест падает т.к. фактический текст ошибки не соответствует тексту ошибки в документации.
    @Test
    @DisplayName("Негативный сценарий создания курьера.Создание двух одинаковых курьеров")
    @Description("Проверка сообщения об ошибке при создании двух одинаковых курьеров")
    @Step("Check error message when creating two identical couriers")
    public void errorMessageInCreatingCouriersWithIdenticalLoginTest() {
        Courier courier = Courier.getRandom();

        courierClient.create(courier);
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));
        String actual = courierClient.create(courier).assertThat()
                .statusCode(409)
                .extract()
                .path("message");;

        assertThat("Error text for creating a courier with identical login has been changed", actual, equalTo("Этот логин уже используется"));
    }
}
