package helpers;


import java.time.Instant;
import java.util.Random;
/**
 * Класс для генерации тестовых данных.
 *
 * @author Zakirova Irina
 */
public class TestData {
    private static final Random random = new Random();

    public static String randomEmail() {
        return "user_" + Instant.now().getEpochSecond() + random.nextInt(9999) + "@test.com";
    }

    public static String randomPassword() {
        return "Password" + random.nextInt(999999) + "!";
    }

    public static String randomName() {
        String[] names = {"Иван", "Мария", "Алексей", "Екатерина"};
        return names[random.nextInt(names.length)] + "_" + random.nextInt(1000);
    }

    public static String randomAdTitle() {
        return "Объявление " + System.currentTimeMillis();
    }

    public static String randomAdDescription() {
        return "Описание объявления " + System.currentTimeMillis();
    }

    public static String randomPrice() {
        return String.valueOf(1000 + random.nextInt(9000));
    }
}