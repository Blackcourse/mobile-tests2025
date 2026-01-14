package drivers;

import com.codeborne.selenide.WebDriverProvider;
import configs.MobileConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

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

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserstack.user", config.browserstackUser());
        caps.setCapability("browserstack.key", config.browserstackKey());

        caps.setCapability("device", config.device());
        caps.setCapability("osVersion", config.os_version());

        try {
            String platform = config.platform().toLowerCase();

            if (platform.equals("android")) {
                MutableCapabilities options = settingsForAndroid(caps);
                return new RemoteWebDriver(new URL(HUB_URL), options);
            }

       /*     if (platform.equals("ios")) {
                XCUITestOptions options = settingsForIos(caps);
                return new IOSDriver(new URL(HUB_URL), options);
            }*/

            throw new IllegalArgumentException("Unknown platform: " + config.platform());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    MutableCapabilities settingsForAndroid(MutableCapabilities caps) {
      //  UiAutomator2Options options = new UiAutomator2Options();
      //  options.setCapability("platformName", "android");
        caps.setCapability("app", "bs://sample.app");
        caps.setCapability("appium:automationName", "UiAutomator2");
      //  caps.setCapability("bstack:options", bstackOptions);
        return caps;
    }

    XCUITestOptions settingsForIos(Map<String, Object> bstackOptions) {
        XCUITestOptions options = new XCUITestOptions();
      //  options.setCapability("platformName", "ios");
        options.setCapability("app", "bs://sample.app");
        options.setCapability("appium:automationName", "XCUITest");
        options.setCapability("bstack:options", bstackOptions);
        return options;
    }



    }


