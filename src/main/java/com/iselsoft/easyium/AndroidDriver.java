package com.iselsoft.easyium;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

public class AndroidDriver extends WebDriver {

    public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(remoteAddress, desiredCapabilities));
    }

    public AndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(remoteAddress, httpClientFactory, desiredCapabilities));
    }

    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(service, desiredCapabilities));
    }

    public AndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(service, httpClientFactory, desiredCapabilities));
    }

    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(builder, desiredCapabilities));
    }

    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(builder, httpClientFactory, desiredCapabilities));
    }

    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(httpClientFactory, desiredCapabilities));
    }

    public AndroidDriver(Capabilities desiredCapabilities) {
        super(new io.appium.java_client.android.AndroidDriver<AndroidElement>(desiredCapabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.ANDROID;
    }
}
