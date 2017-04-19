package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.HttpCommandExecutor;

public class PhantomJSDriver extends WebDriver {

    /**
     * Creates a new PhantomJSDriver instance. The instance will have a default set of desired capabilities.
     *
     * @see org.openqa.selenium.phantomjs.PhantomJSDriverService#createDefaultService() for configuration details.
     */
    public PhantomJSDriver() {
        super(new org.openqa.selenium.phantomjs.PhantomJSDriver());
    }

    /**
     * Creates a new PhantomJSDriver instance.
     *
     * @param desiredCapabilities The capabilities required from PhantomJS/GhostDriver.
     * @see org.openqa.selenium.phantomjs.PhantomJSDriverService#createDefaultService() for configuration details.
     */
    public PhantomJSDriver(Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.phantomjs.PhantomJSDriver(desiredCapabilities));
    }

    /**
     * Creates a new PhantomJSDriver instance. The {@code service} will be started along with the
     * driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service             The service to use.
     * @param desiredCapabilities The capabilities required from PhantomJS/GhostDriver.
     */
    public PhantomJSDriver(PhantomJSDriverService service, Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.phantomjs.PhantomJSDriver(service, desiredCapabilities));
    }

    /**
     * Creates a new PhantomJSDriver instance using the given HttpCommandExecutor.
     *
     * @param executor            The command executor to use
     * @param desiredCapabilities The capabilities required from PhantomJS/GhostDriver.
     */
    public PhantomJSDriver(HttpCommandExecutor executor, Capabilities desiredCapabilities) {
        super(new org.openqa.selenium.phantomjs.PhantomJSDriver(executor, desiredCapabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.PHANTOMJS;
    }
}
