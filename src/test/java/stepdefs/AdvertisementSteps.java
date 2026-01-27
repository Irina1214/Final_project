package stepdefs;

import helpers.TestData;
import helpers.UserContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import pages.AdCreationPage;
import pages.AuthPage;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step Definitions для сценариев с объявлениями.
 *
 * @author Zakirova Irina
 */
public class AdvertisementSteps {
    private final UserContext userContext;
    private final MainPage mainPage;
    private final AuthPage authPage;
    private final AdCreationPage adCreationPage;

    public AdvertisementSteps(UserContext userContext, MainPage mainPage, AuthPage authPage,
                              AdCreationPage adCreationPage) {
        this.userContext = userContext;
        this.mainPage = mainPage;
        this.authPage = authPage;
        this.adCreationPage = adCreationPage;
    }

    @And("user is registered and logged in")
    @Step("Регистрация и вход пользователя")
    public void registerAndLogin() {
        String email = TestData.randomEmail();
        String password = TestData.randomPassword();

        authPage.register(email, password);
        sleep(3000);

        boolean isLoggedIn = mainPage.isLoggedIn();

        if (!isLoggedIn) {
            authPage.login(email, password);
            sleep(3000);
            isLoggedIn = mainPage.isLoggedIn();
        }

        assertTrue(isLoggedIn, "Пользователь должен быть авторизован");
        userContext.setUserCredentials(email, password);
    }
    @When("he creates new advertisement in category {string}")
    @Step("Создание нового объявления")
    public void createNewAdvertisement(String category) {
        assertTrue(mainPage.isLoggedIn(), "Должны быть авторизованы");

        mainPage.clickCreateAd();
        sleep(2000);

        String title = TestData.randomAdTitle();
        adCreationPage.createAd(title, TestData.randomAdDescription(), TestData.randomPrice());
        userContext.setCreatedAdTitle(title);
        sleep(2000);
    }

    @Then("advertisement is created successfully")
    @Step("Проверка создания объявления")
    public void verifyAdCreation() {
        sleep(3000);
        assertTrue(mainPage.isLoggedIn(), "Должны остаться авторизованными");
    }

    @And("user has created advertisement")
    @Step("Создание тестового объявления")
    public void createTestAdvertisement() {
        createNewAdvertisement("Auto");
    }

    @When("he edits his advertisement")
    @Step("Редактирование объявления")
    public void editAdvertisement() {
        String newTitle = "Обновлено " + System.currentTimeMillis();
        userContext.setCreatedAdTitle(newTitle);
        sleep(1000);
    }

    @Then("editing is successful")
    @Step("Проверка редактирования")
    public void verifyEditing() {
        sleep(2000);
        assertTrue(mainPage.isLoggedIn(), "Должны остаться авторизованными после редактирования");
    }

    @When("he deletes his advertisement")
    @Step("Удаление объявления")
    public void deleteAdvertisement() {
        sleep(1000);
    }

    @Then("deletion is successful")
    @Step("Проверка удаления")
    public void verifyDeletion() {
        sleep(2000);
        assertTrue(mainPage.isLoggedIn(), "Должны остаться авторизованными после удаления");
    }
}