package org.example.code_api_data;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.example.url.Uri;

public class Orders {

    public static Response create(JSONObject body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body((new JSONObject(body)).toJSONString())
                .when().post(Uri.ORDERS);

    }

    public static Response getAll() {
        return RestAssured
                .get(Uri.ORDERS);
    }

}
