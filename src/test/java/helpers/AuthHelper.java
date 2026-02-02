package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

/**
 * Класс для очистки авторизации.
 *
 * @author Zakirova Irina
 */
public class AuthHelper {

    @Step("Очистить авторизацию")
    public static void clearAuth() {
        try {
            Selenide.executeJavaScript(
                    "localStorage.removeItem('token');" +
                            "localStorage.removeItem('access_token');" +
                            "localStorage.removeItem('islogin');" +
                            "localStorage.removeItem('user');" +
                            "localStorage.removeItem('user_id');" +
                            "localStorage.removeItem('user_email');" +
                            "localStorage.removeItem('isAuthenticated');" +
                            "localStorage.removeItem('auth_status');" +
                            "localStorage.clear();"
            );
        } catch (Exception e) {
            System.out.println("Ошибка при очистке авторизации: " + e.getMessage());
        }
    }
}