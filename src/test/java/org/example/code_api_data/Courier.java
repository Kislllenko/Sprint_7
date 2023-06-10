package org.example.code_api_data;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.url.Uri;


public class Courier {

    @Step("POST запрос для создания курьера")
    public static Response create(Object body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .when().post(Uri.COURIER);
    }

    @Step("POST запрос для логина курьера")
    public static Response login(Object body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .when().post(Uri.COURIER_LOGIN);
    }

    @Step("DELETE запрос для удаления курьера")
    public static void delete(int id) {
        RestAssured
                .delete(String.format(Uri.COURIER + "/%d", id));
    }
}
