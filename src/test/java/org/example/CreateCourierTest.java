package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import java.io.FileReader;
import java.io.IOException;

import org.example.code_api_data.Courier;

import static org.hamcrest.Matchers.*;

public class CreateCourierTest {

    JSONObject createCourier;
    JSONObject createWithEmptyField;
    JSONObject loginCourier;

    @Before
    public void setUp() throws IOException, ParseException {
        createCourier = (JSONObject)
                (new JSONParser()).parse(new FileReader("src/test/resources/CreateCourier.json"));
        createWithEmptyField = (JSONObject)
                (new JSONParser()).parse(new FileReader("src/test/resources/CreateWithEmptyField.json"));
        loginCourier = (JSONObject)
                (new JSONParser()).parse(new FileReader("src/test/resources/LoginCourier.json"));
    }

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Запрос возвращает правильный код ответа — 201. Успешный запрос возвращает \"ok\": \"true\"")
    public void ableToCreateCourier() {
        Courier.create(createCourier).then().statusCode(201);

        int courierId = Courier.login(loginCourier).jsonPath().get("id");

        Courier.delete(courierId);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void notAbleCreateTheSameCourier() {
        Courier.create(createCourier);

        Response createRes = Courier.create(createCourier);
        createRes.then()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        int courierId = Courier.login(loginCourier).jsonPath().get("id");

        Courier.delete(courierId);
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void forCourierCreationAllFieldsRequired() {
        Response createRes = Courier.create(createWithEmptyField);
        createRes.then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
