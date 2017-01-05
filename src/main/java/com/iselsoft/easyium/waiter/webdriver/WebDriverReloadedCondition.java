package com.iselsoft.easyium.waiter.webdriver;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;

public class WebDriverReloadedCondition extends WebDriverCondition {
    private final Element indicator;

    protected WebDriverReloadedCondition(WebDriver webDriver, Element indicator) {
        super(webDriver);
        this.indicator = indicator;
    }

    @Override
    public boolean occurred() {
        try {
            indicator.seleniumElement().isDisplayed();
            return false;
        } catch (StaleElementReferenceException e) {
            return true;
        }
    }

    @Override
    public String toString() {
        return String.format("Reloaded [\n%s\n]", webDriver);
    }
}
