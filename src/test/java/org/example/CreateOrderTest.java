package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.code_api_data.Orders;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasKey;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    @Parameterized.Parameters
    public static Collection<Object[]> ordersColors() {
        return Arrays.asList(
                new Object[] [] {
                        { new String[] {"BLACK"} },
                        { new String[] {"GRAY"} },
                        { new String[] {"BLACK", "GRAY"} },
                        { new String[] {} },}
                );
    }

    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Можно указать один из цветов — BLACK или GREY, указать оба цвета или не указывать их вообще. " +
            "При создании заказа должен возвращаться его трекинговый номер — track.")
    public void CreateOrdersTest() {
        HashMap<String, Object> orderBody = new HashMap<>();

        orderBody.put("firstName", "Naruto");
        orderBody.put("lastName", "Uchiha");
        orderBody.put("address", "Konoha, 142 apt.");
        orderBody.put("metroStation", 4);
        orderBody.put("phone", "+7 800 355 35 35");
        orderBody.put("rentTime", 5);
        orderBody.put("deliveryDate", "2020-06-06");
        orderBody.put("comment", "Saske, come back to Konoha");
        orderBody.put("color", Arrays.asList(color));

        Response result = Orders.create(new JSONObject(orderBody));
        result.then().statusCode(201).and().body("$", hasKey("track"));
    }

}
