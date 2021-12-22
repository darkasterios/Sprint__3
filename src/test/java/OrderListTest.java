import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderListTest {
    @Test
    @DisplayName("Получение списка заказов.")
    @Description("Проверка что возвращаемый список заказов не пустой")
    @Step("Checking what array orders in not empty")
    public void getOrderListTest(){
        OrderClient orderClient = new OrderClient();

        ValidatableResponse actual = orderClient.getOrdersList().statusCode(200).and().assertThat().body("orders.id", notNullValue());

       assertThat("Array orders returns the Null Value id",actual,notNullValue());
    }
}
