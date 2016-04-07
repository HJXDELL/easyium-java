package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriver extends WebDriver {

    public EdgeDriver() {
        super(new org.openqa.selenium.edge.EdgeDriver());
    }

    public EdgeDriver(EdgeDriverService service) {
        super(new org.openqa.selenium.edge.EdgeDriver(service));
    }

    public EdgeDriver(Capabilities capabilities) {
        super(new org.openqa.selenium.edge.EdgeDriver(capabilities));
    }

    public EdgeDriver(EdgeOptions options) {
        super(new org.openqa.selenium.edge.EdgeDriver(options));
    }

    public EdgeDriver(EdgeDriverService service, EdgeOptions options) {
        super(new org.openqa.selenium.edge.EdgeDriver(service, options));
    }

    public EdgeDriver(EdgeDriverService service, Capabilities capabilities) {
        super(new org.openqa.selenium.edge.EdgeDriver(service, capabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.EDGE;
    }
}
