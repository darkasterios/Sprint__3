import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final List<String> color;
    private final Matcher<Object> expected;

    public CreateOrderTest(List<String> color, Matcher<Object> expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {List.of("BLACK", "GREY"), notNullValue()},
                {List.of("BLACK"), notNullValue()},
                {List.of("GREY"), notNullValue()},
                {null, notNullValue()}
        };
    }

    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Позитивный сценарий создания заказа.")
    @Description("Проверяем создание заказа в различными комбинациями color.")
    public void createOrderTest() {
        Order order = new Order(color);
        Response response = orderClient.createOrder(order);
        response.then().assertThat().statusCode(201)
                .and()
                .body("track", expected);
    }
}
