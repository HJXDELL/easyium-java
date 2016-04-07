package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriver extends WebDriver {
    public SafariDriver() {
        super(new org.openqa.selenium.safari.SafariDriver());
    }

    public SafariDriver(Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.safari.SafariDriver(desiredCapabilities));
    }

    public SafariDriver(SafariOptions safariOptions) {
        super(new org.openqa.selenium.safari.SafariDriver(safariOptions));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.SAFARI;
    }
}
