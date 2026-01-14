package drivers;

import com.codeborne.selenide.WebDriverProvider;
import configs.MobileConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class BrowserstackDriver implements WebDriverProvider {

    private static final String HUB_URL = "https://hub.browserstack.com/wd/hub";

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        final MobileConfig config = ConfigFactory.create(
                MobileConfig.class, System.getProperties()
        );

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", config.browserstackUser());
        bstackOptions.put("accessKey",config.browserstackKey());

        bstackOptions.put("deviceName", config.device());
        bstackOptions.put("osVersion", config.os_version());
        bstackOptions.put("projectName", "Run tests on" + config.device());
        bstackOptions.put("buildName", "build-for-" + config.device() + config.platform());
        bstackOptions.put("sessionName", "tests");

        try {
            String platform = config.platform().toLowerCase();

            if (platform.equals("android")) {
                UiAutomator2Options options = settingsForAndroid(bstackOptions);
                return new AndroidDriver(new URL(HUB_URL), options);
            }

            if (platform.equals("ios")) {
                XCUITestOptions options = settingsForIos(bstackOptions);
                return new IOSDriver(new URL(HUB_URL), options);
            }

            throw new IllegalArgumentException("Unknown platform: " + config.platform());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    UiAutomator2Options settingsForAndroid(Map<String, Object> bstackOptions) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("platformName", "android");
        options.setCapability("app", "bs://sample.app");
        options.setCapability("appium:automationName", "UiAutomator2");
        options.setCapability("bstack:options", bstackOptions);
        return options;
    }

    XCUITestOptions settingsForIos(Map<String, Object> bstackOptions) {
        XCUITestOptions options = new XCUITestOptions();
        options.setCapability("platformName", "ios");
        options.setCapability("app", "bs://sample.app");
        options.setCapability("appium:automationName", "XCUITest");
        options.setCapability("bstack:options", bstackOptions);
        return options;
    }



    }


