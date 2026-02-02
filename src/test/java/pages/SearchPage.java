package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

/**
 * Класс для поиска объявления.
 *
 * @author Zakirova Irina
 */
public class SearchPage {

    public SelenideElement searchInput() {
        return $("input[placeholder='Я хочу купить...']");
    }

    public SelenideElement applyButton() {
        return $x("//button[text()='Применить' and contains(@class, 'buttonSecondary')]");
    }

    @Step("Найти объявление по заголовку")
    public void searchAdByTitle(String title) {

        executeJavaScript("window.scrollTo(0, 0);");

        searchInput().shouldBe(Condition.visible, Duration.ofSeconds(5)).clear();
        searchInput().setValue(title);

        if (applyButton().exists() && applyButton().isDisplayed()) {
            applyButton().click();
        } else {
            searchInput().pressEnter();
        }
    }

    @Step("Проверить, найдено ли объявление")
    public boolean isAdFound(String title) {
        ElementsCollection allCards = $$(".card, [class*='picture'], [class*='card'], [class*='item']");
        for (SelenideElement card : allCards) {
            SelenideElement titleElement = card.$("h2.h2, h2, h3, [class*='title']");
            if (titleElement.exists() && titleElement.getText().equals(title)) {
                return true;
            }
        }
        return false;
    }
}