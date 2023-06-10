package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.example.pojo.CreateCourierJson;
import org.example.code_api_data.Courier;
import org.example.pojo.LoginCourierJson;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@DisplayName("Авторизация")
public class LoginCourierTest {

    CreateCourierJson createCourier = new CreateCourierJson("Autotester", "1Qwe%", "Li");
    LoginCourierJson loginCourier = new LoginCourierJson("Autotester", "1Qwe%");
    LoginCourierJson loginCourierWithoutLoginField = new LoginCourierJson("","1Qwe%");
    LoginCourierJson loginNotExistCourier = new LoginCourierJson("Autotester1","1Qwe%");

    @Before
    public void setUp() {
        Courier.create(createCourier);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Запрос возвращает правильный код ответа — 200. Успешный запрос возвращает \"id\", " +
            "который необходим для удаления курьера")
    public void courierCouldLogin() {
        Response loginRes = Courier.login(loginCourier);

        loginRes.then().statusCode(200).and().body("$", hasKey("id"));
        Assert.assertTrue("id's got to be a number", loginRes.body().jsonPath().get("id") instanceof Number);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля")
    @Description("Если какого-то поля нет, запрос возвращает ошибку")
    public void allFieldsRequired() {
        Response loginRes = Courier.login(loginCourierWithoutLoginField);
        loginRes.then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Несуществующий пользователь")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void notExistingCourierReturnsError() {
        Response loginRes = Courier.login(loginNotExistCourier);
        loginRes.then().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {

        int courierId = Courier.login(loginCourier).jsonPath().get("id");

        Courier.delete(courierId);
    }
}
