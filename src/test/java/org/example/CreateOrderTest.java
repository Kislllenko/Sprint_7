package org.example;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.code_api_data.Orders;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasKey;

@DisplayName("Cоздание заказа")
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

        Faker faker = new Faker();
        String deliveryDate =  new SimpleDateFormat("d.MM.yyyy").format(Calendar.getInstance().getTime());

        HashMap<String, Object> orderBody = new HashMap<>();
        orderBody.put("firstName", faker.name().firstName());
        orderBody.put("lastName", faker.name().lastName());
        orderBody.put("address", faker.address().streetAddress());
        orderBody.put("metroStation", faker.address().state());
        orderBody.put("phone", faker.phoneNumber().phoneNumber());
        orderBody.put("rentTime", faker.number().randomDigit());
        orderBody.put("deliveryDate", deliveryDate);
        orderBody.put("comment", faker.name().title());
        orderBody.put("color", Arrays.asList(color));

        Response result = Orders.create(new JSONObject(orderBody));
        result.then().statusCode(201).and().body("$", hasKey("track"));
    }

}
