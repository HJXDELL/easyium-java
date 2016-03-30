package com.iselsoft.easyium.waiter.webdriver;

import com.iselsoft.easyium.WebDriver;

public class WebDriverActivityPresentCondition extends WebDriverCondition {
    private final String activity;

    public WebDriverActivityPresentCondition(WebDriver webDriver, String activity) {
        super(webDriver);
        this.activity = activity;
    }

    @Override
    public boolean occurred() {
        return true; // todo:
    }

    @Override
    public String toString() {
        return String.format("ActivityPresent [webdriver: \n%s\n][activity: %s]", webDriver, activity);
    }
}
