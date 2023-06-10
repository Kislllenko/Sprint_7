package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.code_api_data.Orders;
import org.junit.Test;
import static org.hamcrest.Matchers.hasKey;

@DisplayName("Получение списка заказов")
public class GetListOrdersTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("В тело ответа должен возвращаться список заказов.")
    public void getOrdersTest() {
        Response result = Orders.getAll();
        result.then().statusCode(200).and().body("$", hasKey("orders"));
    }

}
