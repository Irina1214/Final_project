package stepdefs;

import api.UserApi;
import com.codeborne.selenide.Selenide;
import helpers.TestData;
import helpers.UserContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.AuthPage;
import pages.MainPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions для сценариев регистрации.
 *
 * @author Zakirova Irina
 */
public class RegistrationSteps {
    private final UserContext userContext;
    private final MainPage mainPage;
    private final AuthPage authPage;

    public RegistrationSteps(UserContext userContext, MainPage mainPage, AuthPage authPage) {
        this.userContext = userContext;
        this.mainPage = mainPage;
        this.authPage = authPage;
    }

    @Given("user is on main page")
    @Step("Пользователь на главной странице")
    public void userIsOnMainPage() {
        mainPage.openMainPage();
        mainPage.waitForPageLoad();
        mainPage.authButton().shouldBe(visible);
    }

    @When("user registers with unique email via UI")
    @Step("Регистрация с уникальным email через UI")
    public void registerWithUniqueEmail() {
        String email = TestData.randomEmail();
        String password = TestData.randomPassword();

        authPage.register(email, password);

        userContext.setUserCredentials(email, password);
        userContext.setCreatedViaApi(false);
    }

    @Then("user is successfully registered")
    @Step("Пользователь успешно зарегистрирован")
    public void user_is_successfully_registered() {
        Selenide.sleep(2000);

        authPage.authModal()
                .shouldNotBe(visible, Duration.ofSeconds(5));

        String currentUrl = webdriver().driver().url();
        assertTrue(currentUrl.contains("qa-desk.stand.praktikum-services.ru"),
                "After registration should stay on main page");

        boolean hasError = authPage.hasError();
        assertFalse(hasError, "No errors should appear on successful registration");

        $("input[placeholder='Я хочу купить...']")
                .shouldBe(visible, Duration.ofSeconds(5));
    }

    @When("user tries to register with existing email via UI")
    @Step("Попытка регистрации с существующим email через UI")
    public void registerWithExistingEmail() {
        String email = TestData.randomEmail();
        String password = TestData.randomPassword();
        String name = TestData.randomName();

        String token = UserApi.registerUser(email, password, name);

        assertNotNull(token, "User should be created successfully via API");

        userContext.setUserCredentials(email, password);
        userContext.setAccessToken(token);
        userContext.setCreatedViaApi(true);

        open("/");

        authPage.register(email, "DifferentPassword123!");
    }

    @Then("registration error is displayed")
    @Step("Проверка отображения ошибки")
    public void verifyRegistrationError() {
        authPage.errorMessage()
                .shouldBe(visible, Duration.ofSeconds(5));

        String errorText = authPage.getErrorText();
        assertFalse(errorText.isEmpty(), "Error text should not be empty");

        authPage.authModal()
                .shouldBe(visible);

        authPage.emailField()
                .shouldBe(visible);
    }
}