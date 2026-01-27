package stepdefs;

import config.TestConfig;
import helpers.UserContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс с хуками для настройки и завершения тестов.
 *
 * @author Zakirova Irina
 */
public class Hooks {
    private final UserContext userContext;
    private final MainPage mainPage;

    public Hooks(UserContext userContext, MainPage mainPage) {
        this.userContext = userContext;
        this.mainPage = mainPage;
    }

    @Before
    public void setUp() {
        TestConfig.setup();
        mainPage.openMainPage();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            screenshot(scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"));
        }

        userContext.clear();
        closeWebDriver();
    }
}