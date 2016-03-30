package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;

public class OperaDriver extends WebDriver {

    public OperaDriver() {
        super(new org.openqa.selenium.opera.OperaDriver());
    }

    public OperaDriver(OperaDriverService service) {
        super(new org.openqa.selenium.opera.OperaDriver(service));
    }

    public OperaDriver(Capabilities capabilities) {
        super(new org.openqa.selenium.opera.OperaDriver(capabilities));
    }

    public OperaDriver(OperaOptions options) {
        super(new org.openqa.selenium.opera.OperaDriver(options));
    }

    public OperaDriver(OperaDriverService service, OperaOptions options) {
        super(new org.openqa.selenium.opera.OperaDriver(service, options));
    }

    public OperaDriver(OperaDriverService service, Capabilities capabilities) {
        super(new org.openqa.selenium.opera.OperaDriver(service, capabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.OPERA;
    }
}
