package com.iselsoft.easyium.waiter.webdriver;

import com.iselsoft.easyium.WebDriver;
import org.openqa.selenium.NoAlertPresentException;

public class WebDriverAlertPresentCondition extends WebDriverCondition {

    public WebDriverAlertPresentCondition(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public boolean occurred() {
        try {
            String alertText = webDriver.seleniumWebDriver().switchTo().alert().getText();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("AlertPresent [\n%s\n]", webDriver);
    }
}
