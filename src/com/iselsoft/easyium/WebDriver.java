package com.iselsoft.easyium;

import com.iselsoft.easyium.waiter.webdriver.WebDriverWaitFor;
import org.openqa.selenium.SearchContext;

public abstract class WebDriver<T extends org.openqa.selenium.WebDriver> extends Context {
    public final static long DEFAULT_WAIT_INTERVAL = 1000;
    public final static long DEFAULT_WAIT_TIMEOUT = 30000;
    
    protected T seleniumWebDriver;


    public WebDriver() {
        this(DEFAULT_WAIT_INTERVAL, DEFAULT_WAIT_TIMEOUT);
    }

    public WebDriver(long waitTimeout) {
        this(DEFAULT_WAIT_INTERVAL, waitTimeout);
    }

    public WebDriver(long waitInterval, long waitTimeout) {
        super();
        setWaitInterval(waitInterval);
        setWaitTimeout(waitTimeout);
    }
    
    @Override
    public WebDriver getWebDriver() {
        return this;
    }
        
    @Override
    protected SearchContext seleniumContext() {
        return seleniumWebDriver;
    }
    
    public T seleniumWebDriver() {
        return seleniumWebDriver;
    }
    
    @Override
    protected void refreshMe() {} // do nothing

    @Override
    public void persist() {} // do nothing
    
    public WebDriverWaitFor waitFor() {
        return waitFor(getWaitInterval(), getWaitTimeout());
    }
    
    public WebDriverWaitFor waitFor(long timeout) {
        return waitFor(getWaitInterval(), timeout);
    }
    
    public WebDriverWaitFor waitFor(long interval, long timeout) {
        return new WebDriverWaitFor(this, interval, timeout);
    }
}
