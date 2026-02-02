package helpers;

import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * Класс для генерации тестовых данных.
 *
 * @author Zakirova Irina
 */
public class TestData {
    private static final Faker faker = new Faker(new Locale("ru"));

    public static String randomEmail() {
        return "user_" + System.currentTimeMillis() + "_" + faker.number().numberBetween(1000, 9999) + "@test.com";
    }

    public static String randomPassword() {
        return "Password" + faker.number().numberBetween(100000, 999999) + "!";
    }

    public static String randomName() {
        return faker.name().firstName() + "_" + faker.number().numberBetween(1, 1000);
    }

    public static String randomPrice() {
        return String.valueOf(faker.number().numberBetween(100, 10000));
    }
}