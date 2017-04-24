package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;

import java.net.URL;

public class RemoteDriver extends WebDriver {

    public RemoteDriver(CommandExecutor executor, Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(executor, desiredCapabilities, requiredCapabilities));
    }

    public RemoteDriver(CommandExecutor executor, Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(executor, desiredCapabilities));
    }

    public RemoteDriver(Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(desiredCapabilities));
    }

    public RemoteDriver(URL remoteAddress, Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(remoteAddress, desiredCapabilities, requiredCapabilities));
    }

    public RemoteDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(remoteAddress, desiredCapabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.REMOTE;
    }
}
