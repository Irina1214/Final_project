package stepdefs;

import api.UserApi;
import com.codeborne.selenide.SelenideElement;
import helpers.TestData;
import helpers.UserContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.AdCreationPage;
import pages.AdDetailPage;
import pages.MainPage;
import pages.SearchPage;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions для сценариев с объявлениями.
 *
 * @author Zakirova Irina
 */
public class AdvertisementSteps {
    private final UserContext userContext;
    private final MainPage mainPage;
    private final AdCreationPage adCreationPage;
    private final AdDetailPage adDetailPage;
    private final SearchPage searchPage;

    public AdvertisementSteps(UserContext userContext, MainPage mainPage,
                              AdCreationPage adCreationPage, AdDetailPage adDetailPage,
                              SearchPage searchPage) {
        this.userContext = userContext;
        this.mainPage = mainPage;
        this.adCreationPage = adCreationPage;
        this.adDetailPage = adDetailPage;
        this.searchPage = searchPage;
    }

    @Given("user is registered and logged in")
    @Step("Регистрация и вход пользователя через API")
    public void registerAndLogin() {
        String email = TestData.randomEmail();
        String password = TestData.randomPassword();
        String name = TestData.randomName();

        Map<String, Object> userData = UserApi.registerUserFull(email, password, name);
        assertNotNull(userData, "User should be created successfully via API");

        String token = (String) userData.get("token");
        Integer userId = (Integer) userData.get("userId");
        String userName = (String) userData.get("userName");

        userContext.setUserCredentials(email, password);
        userContext.setAccessToken(token);
        userContext.setUserId(userId);
        userContext.setUserName(userName);
        userContext.setCreatedViaApi(true);

        open("/");

        String userJson = String.format(
                "{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"avatar\":null,\"admin\":false}",
                userId, userName, email
        );

        String script = String.format(
                "localStorage.setItem('token', '%s');" +
                        "localStorage.setItem('access_token', '%s');" +
                        "localStorage.setItem('user', '%s');" +
                        "localStorage.setItem('user_id', '%d');" +
                        "localStorage.setItem('user_email', '%s');" +
                        "localStorage.setItem('user_name', '%s');" +
                        "localStorage.setItem('islogin', 'true');" +
                        "localStorage.setItem('isAuthenticated', 'true');" +
                        "localStorage.setItem('auth_status', 'logged_in');",
                token, token, userJson, userId, email, userName
        );

        executeJavaScript(script);

        refresh();
        $("input[placeholder='Я хочу купить...']")
                .shouldBe(visible, Duration.ofSeconds(10));

        verifyAuthStatus();
    }

    @Step("Проверить статус авторизации")
    public void verifyAuthStatus() {
        String currentUrl = webdriver().driver().url();
        assertTrue(currentUrl.contains("qa-desk.stand.praktikum-services.ru"),
                "Should be on correct domain");

        $("input[placeholder='Я хочу купить...']")
                .shouldBe(visible, Duration.ofSeconds(5));

        mainPage.logoutButton()
                .shouldBe(visible, Duration.ofSeconds(5));

        mainPage.authButton()
                .shouldNotBe(visible);
    }

    @When("he creates new advertisement in category {string}")
    @Step("Создание нового объявления")
    public void createNewAdvertisement(String category) {
        verifyAuthStatus();

        mainPage.clickCreateAd();

        adCreationPage.waitForFormLoad();

        long timestamp = System.currentTimeMillis();
        int randomNum = (int) (Math.random() * 100000);
        String title = "TEST_" + timestamp + "_" + randomNum + "_" +
                java.util.UUID.randomUUID().toString().substring(0, 8);
        String description = "Объявление создано автотестом " + timestamp + " " + randomNum;
        String price = String.valueOf(1000 + randomNum % 9000);

        adCreationPage.createAd(title, description, price);

        sleep(3000);

        open("/");
        $("input[placeholder='Я хочу купить...']")
                .shouldBe(visible, Duration.ofSeconds(5));

        userContext.setCreatedAdTitle(title);
        userContext.setCreatedAdDescription(description);
        userContext.setCreatedAdPrice(price);
    }

    @Then("advertisement is created successfully")
    @Step("Проверка создания объявления")
    public void verifyAdCreation() {
        String title = userContext.getCreatedAdTitle();

        open("/");
        $("input[placeholder='Я хочу купить...']").shouldBe(visible, Duration.ofSeconds(10));

        verifyAuthStatus();

        boolean adFound;

        sleep(7000);
        searchPage.searchAdByTitle(title);
        sleep(2000);

        adFound = searchPage.isAdFound(title);

        assertTrue(adFound, "Созданное объявление должно быть найдено в поиске. Заголовок: " + title);
    }

    @And("user has created advertisement")
    @Step("Создание тестового объявления")
    public void createTestAdvertisement() {
        createNewAdvertisement("Auto");
        verifyAdCreation();
    }

    @When("he edits his advertisement")
    @Step("Редактирование объявления")
    public void editAdvertisement() {
        String oldTitle = userContext.getCreatedAdTitle();
        String newTitle = "Обновлено_" + System.currentTimeMillis();
        String newDescription = "Обновленное описание " + System.currentTimeMillis();
        String newPrice = TestData.randomPrice();

        open("/");
        $("input[placeholder='Я хочу купить...']").shouldBe(visible, Duration.ofSeconds(5));

        $("input[placeholder='Я хочу купить...']").setValue(oldTitle);
        $("input[placeholder='Я хочу купить...']").pressEnter();
        sleep(2000);

        String normalizedTitle = new String(oldTitle.getBytes(), StandardCharsets.UTF_8);
        SelenideElement adElement = $x("//*[contains(text(), '" + normalizedTitle + "')]")
                .shouldBe(visible, Duration.ofSeconds(5));
        adElement.click();
        sleep(2000);

        adDetailPage.editAd(newTitle, newDescription, newPrice);

        open("/");
        $("input[placeholder='Я хочу купить...']").shouldBe(visible, Duration.ofSeconds(5));

        userContext.setCreatedAdTitle(newTitle);
        userContext.setCreatedAdDescription(newDescription);
        userContext.setCreatedAdPrice(newPrice);
    }

    @Then("editing is successful")
    @Step("Проверка редактирования")
    public void verifyEditing() {
        String newTitle = userContext.getCreatedAdTitle();

        open("/");
        mainPage.waitForPageLoad();

        verifyAuthStatus();

        searchPage.searchAdByTitle(newTitle);
        sleep(2000);

        boolean adFound = searchPage.isAdFound(newTitle);

        sleep(2000);

        assertTrue(adFound, "Отредактированное объявление должно быть найдено в поиске. Новый заголовок: " + newTitle);
    }

    @When("he deletes his advertisement")
    @Step("Удаление объявления")
    public void deleteAdvertisement() {
        String title = userContext.getCreatedAdTitle();

        open("/");
        $("input[placeholder='Я хочу купить...']").shouldBe(visible, Duration.ofSeconds(5));

        $("input[placeholder='Я хочу купить...']").setValue(title);
        $("input[placeholder='Я хочу купить...']").pressEnter();
        sleep(2000);

        SelenideElement adElement = $x("//*[contains(text(), '" + title + "')]")
                .shouldBe(visible, Duration.ofSeconds(5));
        adElement.click();
        sleep(3000);

        adDetailPage.deleteAd();
    }

    @Then("deletion is successful")
    @Step("Проверка удаления")
    public void verifyDeletion() {
        String deletedTitle = userContext.getCreatedAdTitle();

        open("/");
        mainPage.waitForPageLoad();

        verifyAuthStatus();

        searchPage.searchAdByTitle(deletedTitle);
        sleep(2000);

        boolean adFound = searchPage.isAdFound(deletedTitle);
        assertFalse(adFound, "Удаленное объявление не должно быть найдено в поиске. Заголовок: " + deletedTitle);
    }
}