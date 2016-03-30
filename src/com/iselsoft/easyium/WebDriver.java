package com.iselsoft.easyium;

import com.iselsoft.easyium.waiter.webdriver.WebDriverWaitFor;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Killable;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class WebDriver extends Context {
    public final static long DEFAULT_WAIT_INTERVAL = 1000;
    public final static long DEFAULT_WAIT_TIMEOUT = 30000;
    public final static long DEFAULT_PAGE_LOAD_TIMEOUT = 30000;
    public final static long DEFAULT_SCRIPT_TIMEOUT = 30000;

    protected final org.openqa.selenium.WebDriver seleniumWebDriver;

    public WebDriver(org.openqa.selenium.WebDriver seleniumWebDriver) {
        super();
        this.seleniumWebDriver = seleniumWebDriver;
        setWaitInterval(DEFAULT_WAIT_INTERVAL);
        setWaitTimeout(DEFAULT_WAIT_TIMEOUT);
        manage().timeouts().pageLoadTimeout(DEFAULT_PAGE_LOAD_TIMEOUT, TimeUnit.MILLISECONDS);
        manage().timeouts().setScriptTimeout(DEFAULT_SCRIPT_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    @Override
    public WebDriver getWebDriver() {
        return this;
    }

    @Override
    protected SearchContext seleniumContext() {
        return seleniumWebDriver;
    }

    public org.openqa.selenium.WebDriver seleniumWebDriver() {
        return seleniumWebDriver;
    }

    @Override
    protected void refresh() {
    } // do nothing

    @Override
    public void persist() {
    } // do nothing

    public WebDriverWaitFor waitFor() {
        return waitFor(getWaitInterval(), getWaitTimeout());
    }

    public WebDriverWaitFor waitFor(long timeout) {
        return waitFor(getWaitInterval(), timeout);
    }

    public WebDriverWaitFor waitFor(long interval, long timeout) {
        return new WebDriverWaitFor(this, interval, timeout);
    }

    public void get(String url) {
        seleniumWebDriver.get(url);
    }

    public void open(String url) {
        get(url);
    }

    public String getCurrentUrl() {
        return seleniumWebDriver.getCurrentUrl();
    }

    public String getTitle() {
        return seleniumWebDriver.getTitle();
    }

    public String getPageSource() {
        return seleniumWebDriver.getPageSource();
    }

    public void close_current_window() {
        seleniumWebDriver.close();
    }

    public void close_window(String nameOrHandle) {
        if (nameOrHandle.equals(getCurrentWindowHandle())) {
            close_current_window();
        } else {
            String currentWindowHandle = getCurrentWindowHandle();
            switchTo().window(nameOrHandle);
            close_current_window();
            switchTo().window(currentWindowHandle);
        }
    }

    public String getCurrentWindowHandle() {
        return seleniumWebDriver.getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return seleniumWebDriver.getWindowHandles();
    }

    public Options manage() {
        return seleniumWebDriver.manage();
    }

    public TargetLocator switchTo() {
        return seleniumWebDriver.switchTo();
    }

    public Navigation navigate() {
        return seleniumWebDriver.navigate();
    }

    public Object executeScript(String script, Object... args) {
        Object[] convertedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Element) {
                Element element = (Element) args[i];
                element.waitFor().exists();
                convertedArgs[i] = element.seleniumElement();
            } else {
                convertedArgs[i] = args[i];
            }
        }
        return ((JavascriptExecutor) seleniumWebDriver).executeScript(script, convertedArgs);
    }

    public Object executeAsyncScript(String script, Object... args) {
        Object[] convertedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Element) {
                Element element = (Element) args[i];
                element.waitFor().exists();
                convertedArgs[i] = element.seleniumElement();
            } else {
                convertedArgs[i] = args[i];
            }
        }
        return ((JavascriptExecutor) seleniumWebDriver).executeAsyncScript(script, convertedArgs);
    }

    public Keyboard getKeyboard() {
        return ((HasInputDevices) seleniumWebDriver).getKeyboard();
    }

    public Mouse getMouse() {
        return ((HasInputDevices) seleniumWebDriver).getMouse();
    }

    public Capabilities getCapabilities() {
        return ((HasCapabilities) seleniumWebDriver).getCapabilities();
    }

    public void quit() {
        seleniumWebDriver.quit();
    }
    
    @Override
    public String toString() {
        return String.format("WebDriver <WebDriverType: %s><SessionId: %s>",getWebDriverType(), ((RemoteWebDriver) seleniumWebDriver).getSessionId());
    }
    
    public void kill() {
        checkSupport(WebDriverType.FIREFOX);
        
        ((Killable) seleniumWebDriver).kill();
    }

    public Location getLocation() {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        return ((LocationContext) seleniumWebDriver).location();
    }

    public void setLocation(Location location) {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        ((LocationContext) seleniumWebDriver).setLocation(location);
    }
    
    public LocalStorage getLocalStorage() {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        return ((WebStorage) seleniumWebDriver).getLocalStorage();
    }
    
    public SessionStorage getSessionStorage() {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        return ((WebStorage) seleniumWebDriver).getSessionStorage(); 
    }
}
