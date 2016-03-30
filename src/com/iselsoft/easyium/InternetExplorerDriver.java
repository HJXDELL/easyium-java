package com.iselsoft.easyium;

public class InternetExplorerDriver extends WebDriver {

    public InternetExplorerDriver() {
        super(new org.openqa.selenium.ie.InternetExplorerDriver());
    }

    @Override
    public WebDriverType getWebDriverType() {
        return WebDriverType.IE;
    }
}
