package org.example.code_api_data;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.example.url.Uri;

public class Courier {

    public static Response create(JSONObject body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body.toJSONString())
                .when().post(Uri.COURIER);
    }

    public static Response login(JSONObject body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body.toJSONString())
                .when().post(Uri.COURIER_LOGIN);
    }

    public static void delete(int id) {
        RestAssured
                .delete(String.format(Uri.COURIER + "/%d", id));
    }
}
