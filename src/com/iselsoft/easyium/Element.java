package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.EasyiumException;
import com.iselsoft.easyium.waiter.element.ElementWaitFor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public abstract class Element extends Context{
    protected WebElement seleniumElement;
    protected String locator;
    private final Context parent;
    
    protected Element(Context parent) {
        super();
        this.parent = parent;
    }
    
    @Override
    public WebDriver getWebDriver() {
        return getParent().getWebDriver();
    }

    @Override
    public WebDriverType getWebDriverType() {
        return getParent().getWebDriverType();
    }
    
    public Context getParent() {
        return parent;
    }

    @Override
    protected SearchContext seleniumContext() throws EasyiumException {
        if (seleniumElement == null) {
            refresh();
        }
        return seleniumElement;
    }
    
    public WebElement seleniumElement() throws EasyiumException {
        if (seleniumElement == null) {
            refresh();
        }
        return seleniumElement;
    }
        
    public ElementWaitFor waitFor() {
        return waitFor(getWaitInterval(), getWaitTimeout());
    }
    
    public ElementWaitFor waitFor(long timeout) {
        return waitFor(getWaitInterval(), timeout);
    }
    
    public ElementWaitFor waitFor(long interval, long timeout) {
        return new ElementWaitFor(this, interval, timeout);
    }
    
    public String getAttribute(String name) {
        return null;
    }
    
    public String getText() {
        return null;
    }
    
    public boolean exists() {
        return true;
    }
    
    public boolean isDisplayed() {
        return true;
    }
}
