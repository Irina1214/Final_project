package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object для главной страницы.
 *
 * @author Zakirova Irina
 */
public class MainPage {

    @Step("Открыть главную страницу")
    public void openMainPage() {
        open("/");
    }

    public SelenideElement createAdButton() {
        return $x("//button[text()='Разместить объявление']");
    }

    public SelenideElement logoutButton() {
        return $x("//button[contains(text(), 'Выйти')]");
    }

    @Step("Проверить авторизацию")
    public boolean isLoggedIn() {
        return logoutButton().is(Condition.visible);
    }

    @Step("Нажать кнопку 'Разместить объявление'")
    public void clickCreateAd() {
        createAdButton().shouldBe(Condition.enabled).click();
    }
}