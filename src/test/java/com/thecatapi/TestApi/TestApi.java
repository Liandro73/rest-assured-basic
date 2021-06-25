package com.thecatapi.TestApi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TestApi {

    private String vote_id;
    private String favourite_id;

    @BeforeClass
    public static void urlBase() {
        RestAssured.baseURI = "https://api.thecatapi.com/v1/";
    }

    @Test
    public void cadastro() {

        final String url = "user/passwordlesssignup";
        final String corpoCadastro =
                "{ \"email\": \"dernival.liandro@foton.la\", \"appDescription\": \"Test Api\", \"details\": { \"user_type\": \"personal\"}}";

        Response response = given()
                .contentType("application/json")
                .body(corpoCadastro)
                .when().post(url);

        validacao(response);

    }

    public void votacao() {

        final String url = "votes/";
        final String corpoVoto = "{\"image_id\": \"61h\", \"value\": \"true\", \"sub_id\": \"demo-c26f9f\"}";

        Response response = given()
                .contentType("application/json")
                .body(corpoVoto)
                .when().post(url);

        validacao(response);

        String id = response.jsonPath().getString("id");
        vote_id = id;
        System.out.println("ID da votacao " + id);

    }

    public void deletaVoto() {

        final String url = "votes/{vote_id}";

        Response response = given()
                .contentType("application/json")
                .header("x-api-key", "f5651d2c-f65e-4f2c-8a20-d1f720191869")
                .pathParam("vote_id", vote_id)
                .when().delete(url);

        validacao(response);

    }

    @Test
    public void deletaVotacao() {
        votacao();
        deletaVoto();
    }

    public void favoritar() {

        final String url = "favourites";
        final String corpoFavorito = "{\"image_id\": \"bt0\", \"sub_id\": \"demo-c26f9f\"}";

        Response response = given()
                .contentType("application/json")
                .header("x-api-key", "f5651d2c-f65e-4f2c-8a20-d1f720191869")
                .body(corpoFavorito)
                .when().post(url);

        validacao(response);

        String id = response.jsonPath().getString("id");
        favourite_id = id;
        System.out.println("ID da votacao " + id);

    }

    public void deletaFavorito() {

        final String url = "favourites/{favourite_id}";

        Response response = given()
                .contentType("application/json")
                .header("x-api-key", "f5651d2c-f65e-4f2c-8a20-d1f720191869")
                .pathParam("favourite_id", favourite_id)
                .when().delete(url);

        validacao(response);

    }

    @Test
    public void executarFavoritarDeletar() {
        favoritar();
        deletaFavorito();
    }

    public void validacao(Response response) {
        System.out.println("Retorno da delecao do voto " + response.body().asString());
        response.then().body("message", containsString("SUCCESS")).statusCode(200);
    }

}
