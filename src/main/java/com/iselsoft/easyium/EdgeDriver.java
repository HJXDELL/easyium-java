package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriver extends WebDriver {

    /**
     * Creates a new EdgeDriver using the {@link EdgeDriverService#createDefaultService default}
     * server configuration.
     *
     * @see #EdgeDriver(EdgeDriverService, EdgeOptions)
     */
    public EdgeDriver() {
        super(new org.openqa.selenium.edge.EdgeDriver());
    }

    /**
     * Creates a new EdgeDriver instance. The {@code service} will be started along with the driver,
     * and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @see #EdgeDriver(EdgeDriverService, EdgeOptions)
     */
    public EdgeDriver(EdgeDriverService service) {
        super(new org.openqa.selenium.edge.EdgeDriver(service));
    }

    /**
     * Creates a new EdgeDriver instance. The {@code capabilities} will be passed to the
     * edgedriver service.
     *
     * @param capabilities The capabilities required from the EdgeDriver.
     * @see #EdgeDriver(EdgeDriverService, Capabilities)
     */
    public EdgeDriver(Capabilities capabilities) {
        super(new org.openqa.selenium.edge.EdgeDriver(capabilities));
    }

    /**
     * Creates a new EdgeDriver instance with the specified options.
     *
     * @param options The options to use.
     * @see #EdgeDriver(EdgeDriverService, EdgeOptions)
     */
    public EdgeDriver(EdgeOptions options) {
        super(new org.openqa.selenium.edge.EdgeDriver(options));
    }

    /**
     * Creates a new EdgeDriver instance with the specified options. The {@code service} will be
     * started along with the driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @param options The options to use.
     */
    public EdgeDriver(EdgeDriverService service, EdgeOptions options) {
        super(new org.openqa.selenium.edge.EdgeDriver(service, options));
    }

    /**
     * Creates a new EdgeDriver instance. The {@code service} will be started along with the
     * driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service      The service to use.
     * @param capabilities The capabilities required from the EdgeDriver.
     */
    public EdgeDriver(EdgeDriverService service, Capabilities capabilities) {
        super(new org.openqa.selenium.edge.EdgeDriver(service, capabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.EDGE;
    }
}
