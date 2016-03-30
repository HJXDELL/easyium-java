package com.iselsoft.easyium.waiter.webdriver;

import com.iselsoft.easyium.WebDriver;
import com.iselsoft.easyium.exceptions.EasyiumException;
import org.openqa.selenium.By;

public class WebDriverTextPresentCondition extends WebDriverCondition {
    private final String text;

    public WebDriverTextPresentCondition(WebDriver webDriver, String text) {
        super(webDriver);
        this.text = text;
    }

    @Override
    public boolean occurred() throws EasyiumException {
        try {
            webDriver.seleniumWebDriver().findElement(By.xpath(String.format("//*[contains(., '%s')]", text)));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("TextPresent [webdriver: \n%s\n][text: %s]", webDriver, text);
    }
}
