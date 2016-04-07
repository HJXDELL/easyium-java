package com.iselsoft.easyium.waiter.webdriver;

import com.iselsoft.easyium.WebDriver;
import com.iselsoft.easyium.waiter.Condition;

public abstract class WebDriverCondition implements Condition {
    protected final WebDriver webDriver;

    protected WebDriverCondition(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public abstract boolean occurred();
}
