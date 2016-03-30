package com.iselsoft.easyium.waiter.webdriver;

import com.iselsoft.easyium.WebDriver;

public class WebDriverURLEqualsConditioin extends WebDriverCondition {
    private final String url;

    public WebDriverURLEqualsConditioin(WebDriver webDriver, String url) {
        super(webDriver);
        this.url = url;
    }

    @Override
    public boolean occurred() {
        return webDriver.seleniumWebDriver().getCurrentUrl().equals(url);
    }

    @Override
    public String toString() {
        return String.format("URLEquals [webdriver: \n%s\n][url: %s]", webDriver, url);
    }
}