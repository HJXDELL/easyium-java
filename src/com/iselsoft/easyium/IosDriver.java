package com.iselsoft.easyium;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

public class IOSDriver extends WebDriver {

    public IOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(remoteAddress, desiredCapabilities));
    }

    public IOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(remoteAddress, httpClientFactory, desiredCapabilities));
    }

    public IOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(service, desiredCapabilities));
    }

    public IOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(service, httpClientFactory, desiredCapabilities));
    }

    public IOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(builder, desiredCapabilities));
    }

    public IOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(builder, httpClientFactory, desiredCapabilities));
    }

    public IOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(httpClientFactory, desiredCapabilities));
    }

    public IOSDriver(Capabilities desiredCapabilities) {
        super(new io.appium.java_client.ios.IOSDriver<IOSElement>(desiredCapabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.IOS;
    }
}
