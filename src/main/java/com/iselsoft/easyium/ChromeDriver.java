package com.iselsoft.easyium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriver extends WebDriver {

    /**
     * Creates a new ChromeDriver using the {@link ChromeDriverService#createDefaultService default}
     * server configuration.
     *
     * @see #ChromeDriver(ChromeDriverService, ChromeOptions)
     */
    public ChromeDriver() {
        super(new org.openqa.selenium.chrome.ChromeDriver());
    }

    /**
     * Creates a new ChromeDriver instance. The {@code service} will be started along with the driver,
     * and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @see #ChromeDriver(ChromeDriverService, ChromeOptions)
     */
    public ChromeDriver(ChromeDriverService service) {
        super(new org.openqa.selenium.chrome.ChromeDriver(service));
    }

    /**
     * Creates a new ChromeDriver instance. The {@code capabilities} will be passed to the
     * chromedriver service.
     *
     * @param capabilities The capabilities required from the ChromeDriver.
     * @see #ChromeDriver(ChromeDriverService, Capabilities)
     */
    public ChromeDriver(Capabilities capabilities) {
        super(new org.openqa.selenium.chrome.ChromeDriver(capabilities));
    }

    /**
     * Creates a new ChromeDriver instance with the specified options.
     *
     * @param options The options to use.
     * @see #ChromeDriver(ChromeDriverService, ChromeOptions)
     */
    public ChromeDriver(ChromeOptions options) {
        super(new org.openqa.selenium.chrome.ChromeDriver(options));
    }

    /**
     * Creates a new ChromeDriver instance with the specified options. The {@code service} will be
     * started along with the driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @param options The options to use.
     */
    public ChromeDriver(ChromeDriverService service, ChromeOptions options) {
        super(new org.openqa.selenium.chrome.ChromeDriver(service, options));
    }

    /**
     * Creates a new ChromeDriver instance. The {@code service} will be started along with the
     * driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service      The service to use.
     * @param capabilities The capabilities required from the ChromeDriver.
     */
    public ChromeDriver(ChromeDriverService service, Capabilities capabilities) {
        super(new org.openqa.selenium.chrome.ChromeDriver(service, capabilities));
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.CHROME;
    }
}
