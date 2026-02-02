package config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Класс для настройки тестовой среды.
 *
 * @author Zakirova Irina
 */
public class TestConfig {
    public static void setup() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.baseUrl = "https://qa-desk.stand.praktikum-services.ru";
        Configuration.headless = false;

        RestAssured.baseURI = "https://qa-desk.stand.praktikum-services.ru";
        RestAssured.basePath = "/api";

        RestAssured.requestSpecification = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}