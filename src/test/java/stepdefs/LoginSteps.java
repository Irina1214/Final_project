package stepdefs;

import api.UserApi;
import helpers.TestData;
import helpers.UserContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.AuthPage;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step Definitions для сценариев входа.
 *
 * @author Zakirova Irina
 */
public class LoginSteps {
    private final UserContext userContext;
    private final MainPage mainPage;
    private final AuthPage authPage;

    public LoginSteps(UserContext userContext, MainPage mainPage, AuthPage authPage) {
        this.userContext = userContext;
        this.mainPage = mainPage;
        this.authPage = authPage;
    }

    @And("user is registered via API")
    @Step("Регистрация пользователя через API")
    public void registerViaApi() {
        String email = TestData.randomEmail();
        String password = TestData.randomPassword();
        String name = TestData.randomName();

        String token = UserApi.registerUser(email, password, name);
        assertNotNull(token, "Пользователь должен быть успешно создан через API");

        userContext.setUserCredentials(email, password); // Убрали name
    }

    @When("user logs in via UI")
    @Step("Вход через UI")
    public void loginViaUI() {
        authPage.login(userContext.getEmail(), userContext.getPassword());
    }

    @Then("login is successful")
    @Step("Проверка успешного входа")
    public void verifyLogin() {
        sleep(3000);
        assertTrue(mainPage.isLoggedIn(), "Пользователь должен быть успешно авторизован");
    }
}