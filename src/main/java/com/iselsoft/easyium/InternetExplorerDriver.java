package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriverService;

public class InternetExplorerDriver extends WebDriver {

    public InternetExplorerDriver() {
        super(new org.openqa.selenium.ie.InternetExplorerDriver());
    }

    public InternetExplorerDriver(Capabilities capabilities) {
        super(new org.openqa.selenium.ie.InternetExplorerDriver(capabilities));
    }

    public InternetExplorerDriver(int port) {
        super(new org.openqa.selenium.ie.InternetExplorerDriver(port));
    }

    public InternetExplorerDriver(InternetExplorerDriverService service) {
        super(new org.openqa.selenium.ie.InternetExplorerDriver(service));
    }

    public InternetExplorerDriver(InternetExplorerDriverService service, Capabilities capabilities) {
        super(new org.openqa.selenium.ie.InternetExplorerDriver(service, capabilities));
    }

    public InternetExplorerDriver(InternetExplorerDriverService service, Capabilities capabilities, int port) {
        super(new org.openqa.selenium.ie.InternetExplorerDriver(service, capabilities, port));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.IE;
    }
}
