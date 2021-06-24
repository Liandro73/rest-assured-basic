package com.thecatapi.TestApi;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TestApi {

    private String vote_id;

    @Test
    public void cadastro() {

        final String url = "https://api.thecatapi.com/v1/user/passwordlesssignup";
        final String corpoCadastro =
                "{ \"email\": \"dernival.liandro@foton.la\", \"appDescription\": \"Test Api\", \"details\": { \"user_type\": \"personal\"}}";

        Response response = given().contentType("application/json").body(corpoCadastro).when().post(url);
        response.then().body("message", containsString("SUCCESS")).statusCode(200);

        System.out.println("Retorno do cadastro " + response.body().asString());

    }

    public void votacao() {

        final String url = "https://api.thecatapi.com/v1/votes/";
        final String corpoVoto =
                "{\"image_id\": \"61h\", \"value\": \"true\", \"sub_id\": \"demo-c26f9f\"}";

        Response response = given().contentType("application/json").body(corpoVoto).when().post(url);
        response.then().body("message", containsString("SUCCESS")).statusCode(200);

        System.out.println("Retorno da votacao " + response.body().asString());
        String id = response.jsonPath().getString("id");
        vote_id = id;
        System.out.println("ID da votacao " + id);

    }


    public void deletaVoto() {

        final String url = "https://api.thecatapi.com/v1/votes/{vote_id}";

        Response response = given().contentType("application/json")
                .header("x-api-key", "f5651d2c-f65e-4f2c-8a20-d1f720191869")
                .pathParam("vote_id", vote_id)
                .when().delete(url);

        System.out.println("Retorno da delecao do voto " + response.body().asString());

        response.then().body("message", containsString("SUCCESS")).statusCode(200);


    }

    @Test
    public void deletaVotacao() {
        votacao();
        deletaVoto();
    }

}
