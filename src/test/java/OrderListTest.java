import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class OrderListTest {
    @Test
    @DisplayName("Получение списка заказов.")
    @Description("Проверка что возвращаемый список заказов не пустой")
    public void getOrderListTest(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.getOrdersList();
        response.statusCode(200).and().assertThat().body("orders", is(not(0)));
    }
}
