package stepdefs;

import api.UserApi;
import helpers.TestData;
import helpers.UserContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.AuthPage;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @When("user registers with unique email via UI")
    @Step("Регистрация с уникальным email")
    public void registerWithUniqueEmail() {
        String email = TestData.randomEmail();
        String password = TestData.randomPassword();

        authPage.register(email, password);
        userContext.setUserCredentials(email, password);
    }

    @Then("registration is successful")
    @Step("Проверка успешной регистрации")
    public void verifyRegistration() {
        sleep(3000);
        assertTrue(mainPage.isLoggedIn(), "Пользователь должен быть авторизован после регистрации");
    }

    @When("user tries to register with existing email via UI")
    @Step("Попытка регистрации с существующим email")
    public void registerWithExistingEmail() {
        String email = TestData.randomEmail();
        String password = TestData.randomPassword();
        String name = TestData.randomName();

        String token = UserApi.registerUser(email, password, name);
        assertNotNull(token, "Первый пользователь должен быть успешно создан через API");

        authPage.register(email, password);
        userContext.setUserCredentials(email, password);
    }

    @Then("registration error is displayed")
    @Step("Проверка отображения ошибки")
    public void verifyRegistrationError() {
        sleep(3000);
        boolean stillOnRegistrationForm = authPage.confirmPasswordField().exists();
        assertTrue(stillOnRegistrationForm,
                "При повторной регистрации должна остаться форма регистрации");
    }
}