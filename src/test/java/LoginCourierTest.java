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
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Проверка получения Id курьера.Успешная авторизация.")
    @Description("Проверка получения Id курьера после успешной авторизации.")
    public void getIdAfterLoginTest(){
        courierId = courierClient.loginAndGetId(CourierCredentials.getCourierCredentials(courier));

        assertThat("Courier id is null",courierId,is(not(0)));
    }

    @Test
    @DisplayName("Негативный сценарий авторизации. Авторизация с неверным паролем")
    @Description("Проверка сообщения об ошибке при авторизации с неверным паролем и существующем логином")
    public void loginWithWrongPasswordTest(){
        Courier courier = new Courier("Alex3000", "1234567", "Alex3000");
        String actualMessage = courierClient.loginWithWrongPassword(CourierCredentials.getCourierCredentials(courier));

        assertThat("Error message test of loginWithWrongPasswordTest was changed","Учетная запись не найдена",equalTo(actualMessage));
    }

   @Test
   @DisplayName("Негативный сценарий авторизации. Авторизация без пароля.")
   @Description("Проверка сообщения об ошибке при авторизации с существующем логином, но без поля пароль")
    public void loginWithoutObligatoryFieldTest(){
        String actualMessage = courierClient.loginWithoutPasswordField(CourierWithoutPassword.getLoginCourier(courier));

        assertThat("Message error of loginWithoutObligatoryField has been changed", actualMessage,equalTo("Недостаточно данных для входа"));
   }

   //Тест падает, сервис недоступен.
   @Test
   @DisplayName("Негативный сценарий авторизации. Проверка статус кода при авторизациис несуществующими в базе логином и паролем")
   @Description("Проверка статус кода при авторизации с несуществующей в базе парой логина и пароля")
   public void logInAsNonExistentUserTest(){
        Courier courier = Courier.getRandom();

        int actualStatusCode = courierClient.logInAnGetStatusCode(CourierCredentials.getCourierCredentials(courier));

        assertThat("Status Code of logInAsNonExistentUserTest is differnt at 404",actualStatusCode,equalTo(404));
   }

    @Test
    @DisplayName("Позитивный сценарий авторизации.")
    @Description("Проверка статус кода при авторизации с существующей в базе парой логина и пароля.")
    public void courierCanLoginTest(){
        int actualStatusCode = courierClient.logInAnGetStatusCode(CourierCredentials.getCourierCredentials(courier));

        assertThat("Status code in courierCanLoginTest is not 200",actualStatusCode,equalTo(200));
    }
}
