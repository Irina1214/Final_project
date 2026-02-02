package api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Класс для работы с API пользователей.
 *
 * @author Zakirova Irina
 */
public class UserApi {
    public static Map<String, Object> registerUserFull(String email, String password, String name) {
        String requestBody = String.format(
                "{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                email, password, name
        );

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/signup");

        if (response.statusCode() != 201) {
            return null;
        }

        String token = response.jsonPath().getString("access_token.access_token");
        if (token == null) {
            token = response.jsonPath().getString("access_token");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", response.jsonPath().getInt("user.id"));
        result.put("userName", response.jsonPath().getString("user.name"));
        result.put("email", response.jsonPath().getString("user.email"));
        result.put("password", password);

        return result;
    }

    public static String registerUser(String email, String password, String name) {
        Map<String, Object> result = registerUserFull(email, password, name);
        if (result != null) {
            return (String) result.get("token");
        }
        return null;
    }
}