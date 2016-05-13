package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;

public class OperaDriver extends WebDriver {

    /**
     * Creates a new OperaDriver using the {@link OperaDriverService#createDefaultService default}
     * server configuration.
     *
     * @see #OperaDriver(OperaDriverService, OperaOptions)
     */
    public OperaDriver() {
        super(new org.openqa.selenium.opera.OperaDriver());
    }

    /**
     * Creates a new OperaDriver instance. The {@code service} will be started along with the driver,
     * and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @see #OperaDriver(OperaDriverService, OperaOptions)
     */
    public OperaDriver(OperaDriverService service) {
        super(new org.openqa.selenium.opera.OperaDriver(service));
    }

    /**
     * Creates a new OperaDriver instance. The {@code capabilities} will be passed to the
     * chromedriver service.
     *
     * @param capabilities The capabilities required from the OperaDriver.
     * @see #OperaDriver(OperaDriverService, Capabilities)
     */
    public OperaDriver(Capabilities capabilities) {
        super(new org.openqa.selenium.opera.OperaDriver(capabilities));
    }

    /**
     * Creates a new OperaDriver instance with the specified options.
     *
     * @param options The options to use.
     * @see #OperaDriver(OperaDriverService, OperaOptions)
     */
    public OperaDriver(OperaOptions options) {
        super(new org.openqa.selenium.opera.OperaDriver(options));
    }

    /**
     * Creates a new OperaDriver instance with the specified options. The {@code service} will be
     * started along with the driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @param options The options to use.
     */
    public OperaDriver(OperaDriverService service, OperaOptions options) {
        super(new org.openqa.selenium.opera.OperaDriver(service, options));
    }

    /**
     * Creates a new OperaDriver instance. The {@code service} will be started along with the
     * driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service      The service to use.
     * @param capabilities The capabilities required from the OperaDriver.
     */
    public OperaDriver(OperaDriverService service, Capabilities capabilities) {
        super(new org.openqa.selenium.opera.OperaDriver(service, capabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.OPERA;
    }
}
