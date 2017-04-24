package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxDriver extends WebDriver {

    public FirefoxDriver() {
        super(new org.openqa.selenium.firefox.FirefoxDriver());
    }

    public FirefoxDriver(FirefoxOptions options) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(options));
    }

    public FirefoxDriver(FirefoxBinary binary) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(binary));
    }
    
    public FirefoxDriver(FirefoxProfile profile) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(profile));
    }

    public FirefoxDriver(FirefoxBinary binary, FirefoxProfile profile) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(binary, profile));
    }

    public FirefoxDriver(Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(desiredCapabilities));
    }

    public FirefoxDriver(Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(desiredCapabilities, requiredCapabilities));
    }

    public FirefoxDriver(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(binary, profile, capabilities));
    }

    public FirefoxDriver(FirefoxBinary binary, FirefoxProfile profile, Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.firefox.FirefoxDriver(binary, profile, desiredCapabilities, requiredCapabilities));
    }
    
    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.FIREFOX;
    }
}
