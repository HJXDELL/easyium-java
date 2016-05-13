package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriver extends WebDriver {
    /**
     * Initializes a new SafariDriver} class with default {@link SafariOptions}.
     */
    public SafariDriver() {
        super(new org.openqa.selenium.safari.SafariDriver());
    }

    /**
     * Converts the specified {@link Capabilities} to a {@link SafariOptions}
     * instance and initializes a new SafariDriver using these options.
     *
     * @param desiredCapabilities capabilities requested of the driver
     * @see SafariOptions#fromCapabilities(org.openqa.selenium.Capabilities)
     */
    public SafariDriver(Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.safari.SafariDriver(desiredCapabilities));
    }

    /**
     * Initializes a new SafariDriver using the specified {@link SafariOptions}.
     *
     * @param safariOptions safari specific options / capabilities for the driver
     */
    public SafariDriver(SafariOptions safariOptions) {
        super(new org.openqa.selenium.safari.SafariDriver(safariOptions));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.SAFARI;
    }
}
