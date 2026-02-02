package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object для страницы авторизации и регистрации.
 *
 * @author Zakirova Irina
 */
public class AuthPage {

    public SelenideElement authModalButton() {
        return $x("//button[contains(text(), 'Вход и регистрация')]");
    }

    public SelenideElement authModal() {
        return $x("//div[contains(@class, 'modal') or contains(@class, 'dialog')]");
    }

    public SelenideElement emailField() {
        return $("input[name='email']");
    }

    public SelenideElement passwordField() {
        return $("input[name='password']");
    }

    public SelenideElement confirmPasswordField() {
        return $("input[name='submitPassword']");
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

    public SelenideElement hasAccountButton() {
        return $x("//button[contains(text(), 'Есть аккаунт')]");
    }

    public SelenideElement errorMessage() {
        SelenideElement element = $(".input_inputError__fLUP9");

        if (!element.exists()) {
            element = $x("//*[contains(@class, 'error') or contains(@class, 'Error')]");
        }

        if (!element.exists()) {
            if (emailField().has(cssClass("input_inputError__fLUP9"))) {
                return emailField();
            }
            if (passwordField().has(cssClass("input_inputError__fLUP9"))) {
                return passwordField();
            }
        }
        return element;
    }

    public boolean hasFieldError(SelenideElement field) {
        return field.has(cssClass("input_inputError__fLUP9"));
    }

    @Step("Открыть модальное окно авторизации")
    public void openAuthModal() {
        if (isModalOpen()) {
            return;
        }

        authModalButton().shouldBe(visible).shouldBe(enabled).click();

        authModal().shouldBe(visible);
        emailField().shouldBe(visible);
    }

    @Step("Переключиться на форму регистрации")
    public void switchToRegistration() {
        if (confirmPasswordField().isDisplayed()) {
            return;
        }

        noAccountButton().shouldBe(visible).shouldBe(enabled).click();

        confirmPasswordField().shouldBe(visible).shouldBe(enabled);
        createAccountButton().shouldBe(visible);
    }

    @Step("Переключиться на форму входа")
    public void switchToLogin() {
        if (loginButton().isDisplayed() && !confirmPasswordField().isDisplayed()) {
            return;
        }

        hasAccountButton().shouldBe(visible).shouldBe(enabled).click();

        loginButton().shouldBe(visible);
        confirmPasswordField().shouldNotBe(visible);
    }

    @Step("Зарегистрироваться")
    public void register(String email, String password) {
        openAuthModal();
        switchToRegistration();

        emailField().clear();
        emailField().setValue(email);

        passwordField().clear();
        passwordField().setValue(password);

        confirmPasswordField().clear();
        confirmPasswordField().setValue(password);

        createAccountButton().shouldBe(enabled).click();

        sleep(3000);
    }

    @Step("Войти")
    public void login(String email, String password) {
        openAuthModal();

        if (!loginButton().isDisplayed() && hasAccountButton().isDisplayed()) {
            switchToLogin();
        }

        emailField().clear();
        emailField().setValue(email);

        passwordField().clear();
        passwordField().setValue(password);

        loginButton().shouldBe(enabled).click();

        sleep(3000);
    }

    @Step("Проверить наличие ошибки")
    public boolean hasError() {
        return hasVisibleErrorMessage() || hasFieldErrors() || hasErrorTextOnPage();
    }

    private boolean hasVisibleErrorMessage() {
        return errorMessage().exists() && errorMessage().isDisplayed();
    }

    private boolean hasFieldErrors() {
        return hasFieldError(emailField()) ||
                hasFieldError(passwordField()) ||
                hasFieldError(confirmPasswordField());
    }

    private boolean hasErrorTextOnPage() {
        String pageText = $("body").getText().toLowerCase();
        return pageText.contains("ошибка") ||
                pageText.contains("error") ||
                pageText.contains("уже существует");
    }

    @Step("Получить текст ошибки")
    public String getErrorText() {
        if (!hasError()) {
            return "";
        }

        if (errorMessage().exists() && !errorMessage().getText().isEmpty()) {
            return errorMessage().getText();
        }

        if ($("body").getText().contains("Ошибка")) {
            return "Текст ошибки найден на странице";
        }

        return "Элемент с ошибкой виден, но текст пуст";
    }

    @Step("Проверить, открыто ли модальное окно")
    public boolean isModalOpen() {
        return (authModal().exists() && authModal().isDisplayed()) ||
                (emailField().exists() && emailField().isDisplayed());
    }
}