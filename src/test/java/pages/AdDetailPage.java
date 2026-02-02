package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object для детальной страницы объявления.
 *
 * @author Zakirova Irina
 */
public class AdDetailPage {

    public SelenideElement editButton() {
        return $x("//button[text()='Редактировать объявление']");
    }

    public SelenideElement deleteButton() {
        return $x("//button[text()='Удалить']");
    }

    public SelenideElement titleField() {
        return $("input[name='name']");
    }

    public SelenideElement descriptionField() {
        return $("textarea[name='description']");
    }

    public SelenideElement priceField() {
        return $("input[name='price']");
    }

    public SelenideElement saveButton() {
        return $x("//button[contains(text(), 'Сохранить')]");
    }

    @Step("Редактировать объявление")
    public void editAd(String newTitle, String newDescription, String newPrice) {
        editButton().shouldBe(visible).shouldBe(enabled).click();

        titleField().shouldBe(visible, Duration.ofSeconds(3)).setValue(newTitle);
        descriptionField().setValue(newDescription);
        priceField().setValue(newPrice);

        saveButton().shouldBe(enabled).click();

        sleep(2000);
    }

    @Step("Удалить объявление")
    public void deleteAd() {
        deleteButton().shouldBe(visible).shouldBe(enabled).click();

        sleep(3000);
    }
}