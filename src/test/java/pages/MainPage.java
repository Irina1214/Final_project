package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
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
        waitForPageLoad();
    }

    public SelenideElement createAdButton() {
        return $x("//button[text()='Разместить объявление']");
    }

    public SelenideElement logoutButton() {
        return $x("//button[contains(text(), 'Выйти')]");
    }

    public SelenideElement authButton() {
        return $x("//button[contains(text(), 'Вход')]");
    }

    public SelenideElement searchInput() {
        return $("input[placeholder='Я хочу купить...']");
    }

    @Step("Нажать кнопку 'Разместить объявление'")
    public void clickCreateAd() {
        createAdButton().shouldBe(visible).shouldBe(enabled).click();
    }

    @Step("Дождаться полной загрузки страницы")
    public void waitForPageLoad() {
        searchInput().shouldBe(visible, Duration.ofSeconds(10));
    }
}