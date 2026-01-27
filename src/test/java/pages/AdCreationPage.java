package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object для страницы создания объявления.
 *
 * @author Zakirova Irina
 */
public class AdCreationPage {

    public SelenideElement titleField() {
        return $("input[placeholder='Название']").shouldBe(Condition.visible);
    }

    public SelenideElement descriptionField() {
        return $("textarea[placeholder='Описание товара']").shouldBe(Condition.visible);
    }

    public SelenideElement priceField() {
        return $("input[placeholder='Стоимость']").shouldBe(Condition.visible);
    }

    public SelenideElement publishButton() {
        return $x("//button[text()='Опубликовать']").shouldBe(Condition.enabled);
    }

    @Step("Создать объявление")
    public void createAd(String title, String description, String price) {
        titleField().setValue(title);
        descriptionField().setValue(description);
        priceField().setValue(price);
        publishButton().click();
    }
}