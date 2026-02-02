package stepdefs;

import api.UserApi;
import com.codeborne.selenide.Selenide;
import helpers.TestData;
import helpers.UserContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.AuthPage;
import pages.MainPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
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

        assertNotNull(token, "User should be created successfully via API");

        userContext.setUserCredentials(email, password);
        userContext.setAccessToken(token);
        userContext.setCreatedViaApi(true);
    }

    @When("user logs in via UI")
    @Step("Вход через UI")
    public void loginViaUI() {
        mainPage.openMainPage();
        authPage.login(userContext.getEmail(), userContext.getPassword());
    }

    @Then("login is successful")
    @Step("Проверка успешного входа")
    public void verifyLogin() {

        Selenide.sleep(2000);

        String currentUrl = webdriver().driver().url();
        assertTrue(currentUrl.contains("qa-desk.stand.praktikum-services.ru"),
                "After login should be on main page");

        $("input[placeholder='Я хочу купить...']")
                .shouldBe(visible, Duration.ofSeconds(5));

        mainPage.logoutButton()
                .shouldBe(visible, Duration.ofSeconds(5));

        mainPage.authButton()
                .shouldNotBe(visible);
    }
}