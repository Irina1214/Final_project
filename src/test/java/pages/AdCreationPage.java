package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object для страницы создания объявления.
 *
 * @author Zakirova Irina
 */
public class AdCreationPage {

    public SelenideElement titleField() {
        return $("input[name='name'][placeholder='Название']");
    }

    public SelenideElement descriptionField() {
        return $("textarea[name='description'][placeholder='Описание товара']");
    }

    public SelenideElement priceField() {
        return $("input[name='price'][placeholder='Стоимость']");
    }

    public SelenideElement publishButton() {
        return $x("//button[text()='Опубликовать' and @type='submit']");
    }

    @Step("Дождаться загрузки формы создания объявления")
    public void waitForFormLoad() {
        titleField().shouldBe(Condition.visible, Duration.ofSeconds(10));
        publishButton().shouldBe(Condition.visible);
    }

    @Step("Создать объявление")
    public void createAd(String title, String description, String price) {
        waitForFormLoad();

        titleField().clear();
        titleField().setValue(title);

        descriptionField().clear();
        descriptionField().setValue(description);

        priceField().clear();
        priceField().setValue(price);

        publishButton().shouldBe(Condition.enabled).click();

        sleep(2000);
    }
}