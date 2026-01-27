package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object для страницы авторизации и регистрации.
 *
 * @author Zakirova Irina
 */
public class AuthPage {

    public SelenideElement authModalButton() {
        return $x("//button[text()='Вход и регистрация']");
    }

    public SelenideElement emailField() {
        return $("input[placeholder='Введите Email']");
    }

    public SelenideElement passwordField() {
        return $("input[placeholder='Пароль'][type='password']");
    }

    public SelenideElement confirmPasswordField() {
        return $("input[placeholder='Повторите пароль']");
    }

    public SelenideElement createAccountButton() {
        return $x("//button[text()='Создать аккаунт']");
    }

    public SelenideElement noAccountButton() {
        return $x("//button[text()='Нет аккаунта']");
    }

    public SelenideElement loginButton() {
        return $x("//button[text()='Войти']");
    }

    @Step("Зарегистрироваться")
    public void register(String email, String password) {
        authModalButton().click();
        sleep(1000);

        noAccountButton().click();
        sleep(500);

        emailField().setValue(email);
        sleep(300);
        passwordField().setValue(password);
        sleep(300);
        confirmPasswordField().setValue(password);
        sleep(300);

        createAccountButton().click();
        sleep(3000);
    }

    @Step("Войти")
    public void login(String email, String password) {
        authModalButton().click();
        sleep(1000);

        emailField().setValue(email);
        sleep(300);
        passwordField().setValue(password);
        sleep(300);

        loginButton().click();
        sleep(3000);
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}