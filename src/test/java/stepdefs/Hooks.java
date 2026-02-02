package stepdefs;

import config.TestConfig;
import helpers.AuthHelper;
import helpers.UserContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

/**
 * Класс с хуками для настройки и завершения тестов.
 *
 * @author Zakirova Irina
 */
public class Hooks {
    private final UserContext userContext;

    public Hooks(UserContext userContext) {
        this.userContext = userContext;
    }

    @Before(order = 1)
    public void setUp() {
        TestConfig.setup();

        open("/");

        AuthHelper.clearAuth();
    }

    @After
    public void tearDown() {
        userContext.clear();

        closeWebDriver();
    }
}