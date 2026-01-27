package config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
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

        RestAssured.baseURI = "https://qa-desk.stand.praktikum-services.ru/api";
    }
}