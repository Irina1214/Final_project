package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
/**
 * Класс для работы с API пользователей.
 *
 * @author Zakirova Irina
 */
public class UserApi {

    static {
        RestAssured.baseURI = "https://qa-desk.stand.praktikum-services.ru/api";
    }

    public static String registerUser(String email, String password, String name) {
        String requestBody = String.format(
                "{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                email, password, name
        );

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/signup");

        return response.statusCode() == 201 ?
                response.jsonPath().getString("access_token") :
                null;
    }
}