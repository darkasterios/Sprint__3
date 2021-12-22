import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginCourierTest {
    CourierClient courierClient;
    private int courierId;
    Courier courier = new Courier("Alex3000", "123456", "Alex3000");

    @Before
    @Step("Creating a new courierClient for tests")
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Проверка получения Id курьера.Успешная авторизация.")
    @Description("Проверка получения Id курьера после успешной авторизации.")
    @Step("Checking courier id after authorisation")
    public void getIdAfterLoginTest(){
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

        assertThat("Courier id is null",courierId,is(not(0)));
    }

    @Test
    @DisplayName("Негативный сценарий авторизации. Авторизация с неверным паролем")
    @Description("Проверка сообщения об ошибке при авторизации с неверным паролем и существующем логином")
    @Step("Checking error message after authorisation with wrong password an existing login")
    public void loginWithWrongPasswordTest(){
        Courier courier = new Courier("Alex3000", "1234567", "Alex3000");

        String actualMessage = courierClient.login(CourierCredentials.getCourierCredentials(courier)).assertThat()
                .statusCode(404)
                .extract()
                .path("message");;

        assertThat("Error message test of loginWithWrongPasswordTest was changed","Учетная запись не найдена",equalTo(actualMessage));
    }

    //Тест падает, сервис недоступен.
   @Test
   @DisplayName("Негативный сценарий авторизации. Авторизация без пароля.")
   @Description("Проверка сообщения об ошибке при авторизации с существующем логином, но без поля пароль")
   @Step("Checking error message after authorisation without password an existing login")
    public void loginWithoutObligatoryFieldTest(){
        String actualMessage = courierClient.loginWithoutPasswordField(CourierWithoutPassword.getLoginCourier(courier)).assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertThat("Message error of loginWithoutObligatoryField has been changed", actualMessage,equalTo("Недостаточно данных для входа"));
   }

   @Test
   @DisplayName("Негативный сценарий авторизации. Проверка статус кода при авторизациис несуществующими в базе логином и паролем")
   @Description("Проверка статус кода при авторизации с несуществующей в базе парой логина и пароля")
   @Step("Checking Status Code after authorisation with non-existent password and login")
   public void logInAsNonExistentUserTest(){
        Courier courier = Courier.getRandom();

        int actualStatusCode = courierClient.login(CourierCredentials.getCourierCredentials(courier)).assertThat()
                .statusCode(404)
                .extract()
                .statusCode();

        assertThat("Status Code of logInAsNonExistentUserTest is differnt at 404",actualStatusCode,equalTo(404));
   }

    @Test
    @DisplayName("Позитивный сценарий авторизации.")
    @Description("Проверка статус кода при авторизации с существующей в базе парой логина и пароля.")
    @Step("Checking Status Code after authorisation with existent password and login")
    public void courierCanLoginTest(){
        int actualStatusCode = courierClient.login(CourierCredentials.getCourierCredentials(courier)).assertThat()
                .statusCode(200)
                .extract()
                .statusCode();

        assertThat("Status code in courierCanLoginTest is not 200",actualStatusCode,equalTo(200));
    }
}
