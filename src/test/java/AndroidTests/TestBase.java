package AndroidTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserstackDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeAll
    public static void setupEnvironment() {
        Configuration.browser= BrowserstackDriver.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;

    }
    @BeforeEach
    void addSelenideLogger() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void  addAtachments(){
        Attach.screenshotAs("Last screenshot");
        closeWebDriver();

        closeWebDriver();

    }

}
