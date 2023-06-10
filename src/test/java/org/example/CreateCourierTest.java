package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.example.pojo.CreateCourierJson;
import org.example.code_api_data.Courier;
import org.example.pojo.LoginCourierJson;
import static org.hamcrest.Matchers.*;

@DisplayName("Cоздание профиля для курьера")
public class CreateCourierTest {

    CreateCourierJson createCourier = new CreateCourierJson("Autotester", "1Qwe%", "Li");
    CreateCourierJson createWithEmptyField = new CreateCourierJson("", "1Qwe%", "Li");
    LoginCourierJson loginCourier = new LoginCourierJson("Autotester", "1Qwe%");

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
