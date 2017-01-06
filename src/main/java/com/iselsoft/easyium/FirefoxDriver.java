package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;

public class FirefoxDriver extends WebDriver {

    public FirefoxDriver() {
        super(new org.openqa.selenium.firefox.FirefoxDriver());
    }

    public FirefoxDriver(FirefoxProfile profile) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(profile));
    }

    public FirefoxDriver(Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(desiredCapabilities));
    }

    public FirefoxDriver(Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(desiredCapabilities, requiredCapabilities));
    }

    public FirefoxDriver(FirefoxBinary binary, FirefoxProfile profile) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(binary, profile));
    }

    public FirefoxDriver(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(binary, profile, capabilities));
    }

    public FirefoxDriver(FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(binary, profile, desiredCapabilities, requiredCapabilities));
    }
    
    public FirefoxDriver(GeckoDriverService driverService, Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(driverService, desiredCapabilities, requiredCapabilities));
    }
    
    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.FIREFOX;
    }
}
