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
    public void setUp(){
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        if(courierId!=0){
        courierClient.delete(courierId);}
    }

    @Test
    @DisplayName("Позитивный сценарий создания курьера.Проверка возвращаемого id курьера")
    @Description("Проверка успешного создания курьера, возвращаемый id не равен 0")
    public void courierCreateTest(){
        Courier courier = Courier.getRandom();

        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

        assertTrue("Courier is not created",isCreated);
        assertThat("Courier id is incorrect", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Проверка невозможности создания 2-ух одинаковых курьеров.")
    @Description("Проверка статус кода при попытке создания 2-ух одинаковых курьеров")
    public void createIdenticalCouriersTest(){
        Courier courier = Courier.getRandom();

        courierClient.create(courier);
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));
        int actual = courierClient.createExistingCourier(courier);

        assertThat("In Identical couriers creating test status code is not 409",actual,equalTo(409));
    }

    @Test
    @DisplayName("Создание курьера с двумя обязательными полям логин и пароль")
    @Description("Проверка возможности создания курьера с передачей двух обязательных полей логин и пароль")
    public void createCourierWithObligatoryFieldsTest(){
     Courier courier = Courier.getRandom();

     boolean isCreated = courierClient.createCourierWithObligatoryFields(CourierCredentials.getCourierCredentials(courier));
     courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

     assertTrue("Courier is not created",isCreated);
    }

    @Test
    @DisplayName("Позитивный сценарий создания курьера. Тело ответа true")
    @Description("Проверка что при успешном создании курьера возвращаемое body true")
    public void checkSuccessfulRequestBodyCreateCourierTest(){
        Courier courier = Courier.getRandom();

        boolean actual = courierClient.checkSuccessfulRequestBodyAfterCreating(courier);
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

        assertTrue("SuccessfulRequestBody returns false in positive courier creating test", actual);
    }

    @Test
    @DisplayName("Позитивный сценарий создания курьера. Проверка статус кода")
    @Description("Проверка что при успешном создании курьера, возвращаемый статус код = 201")
    public void checkStatusCodePositiveCreatingCourierTest(){
        Courier courier = Courier.getRandom();

        int actual = courierClient.checkStatusCodeAfterCreating(courier);
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

       assertThat("In positive courier creating test status code is not 201",actual,equalTo(201));
    }

    @Test
    @DisplayName("Негативный сценарий создания курьера. Создание курьера без пароля")
    @Description("Проверка сообщения об ошибке при создании курьера без пароля")
    public void createCourierWithoutPasswordFieldTest(){
        Courier courier = Courier.getRandom();

        String actual = courierClient.createWithoutPassword(CourierWithoutPassword.getLoginCourier(courier));

        assertThat("Error text for creating a courier without a password has been changed",actual,equalTo("Недостаточно данных для создания учетной записи"));
    }


    //Тест падает т.к. фактический текст ошибки не соответствует тексту ошибки в документации.
    @Test
    @DisplayName("Негативный сценарий создания курьера.Создание двух одинаковых курьеров")
    @Description("Проверка сообщения об ошибке при создании двух одинаковых курьеров")
    public void errorMessageInCreatingCouriersWithIdenticalLoginTest() {
        Courier courier = Courier.getRandom();

        courierClient.create(courier);
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));
        String actual = courierClient.creatingIdenticalLoginsCouriers(courier);

        assertThat("Error text for creating a courier with identical login has been changed\"", actual, equalTo("Этот логин уже используется"));
    }
}
