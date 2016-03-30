package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriver extends WebDriver {

    public ChromeDriver() {
        super(new org.openqa.selenium.chrome.ChromeDriver());
    }

    public ChromeDriver(ChromeDriverService service) {
        super(new org.openqa.selenium.chrome.ChromeDriver(service));
    }

    public ChromeDriver(Capabilities capabilities) {
        super(new org.openqa.selenium.chrome.ChromeDriver(capabilities));
    }

    public ChromeDriver(ChromeOptions options) {
        super(new org.openqa.selenium.chrome.ChromeDriver(options));
    }

    public ChromeDriver(ChromeDriverService service, ChromeOptions options) {
        super(new org.openqa.selenium.chrome.ChromeDriver(service, options));
    }

    public ChromeDriver(ChromeDriverService service, Capabilities capabilities) {
        super(new org.openqa.selenium.chrome.ChromeDriver(service, capabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.CHROME;
    }
}
