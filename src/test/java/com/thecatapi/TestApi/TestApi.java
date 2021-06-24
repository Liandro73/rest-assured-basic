package com.thecatapi.TestApi;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TestApi {

    @Test
    public void cadastro() {

        final String url = "https://api.thecatapi.com/v1/user/passwordlesssignup";
        final String corpoCadastro =
                "{ \"email\": \"testcatapi@gmail.com\", \"appDescription\": \"Test Api\", \"details\": { \"user_type\": \"personal\"}}";

        Response response = given().contentType("application/json").body(corpoCadastro).when().post(url);
        response.then().body("message", containsString("SUCCESS")).statusCode(200);

        System.out.println("Retorno do cadastro" + response.body().asString());

    }

}
