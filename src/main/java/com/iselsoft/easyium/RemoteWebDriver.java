package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;

import java.net.URL;

public class RemoteWebDriver extends WebDriver {

    public RemoteWebDriver(CommandExecutor executor, Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(executor, desiredCapabilities, requiredCapabilities));
    }

    public RemoteWebDriver(CommandExecutor executor, Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(executor, desiredCapabilities));
    }

    public RemoteWebDriver(Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(desiredCapabilities));
    }

    public RemoteWebDriver(URL remoteAddress, Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(remoteAddress, desiredCapabilities, requiredCapabilities));
    }

    public RemoteWebDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.remote.RemoteWebDriver(remoteAddress, desiredCapabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.REMOTE;
    }
}
