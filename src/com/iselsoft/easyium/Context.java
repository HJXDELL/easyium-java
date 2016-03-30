package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.*;
import com.iselsoft.easyium.waiter.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class Context {
    private long waitInterval;
    private long waitTimeout;
    
    protected Context() {
        waitInterval = -568522662;
        waitTimeout = -568522662;
    }
    
    public abstract WebDriver getWebDriver();
    
    public abstract WebDriverType getWebDriverType();

    protected abstract SearchContext seleniumContext() throws EasyiumException;

    protected abstract void refreshMe() throws EasyiumException;

    public abstract void persist() throws LatePersistException;
    
    public long getWaitInterval() {
        if (waitInterval == -568522662) {
            return getWebDriver().getWaitInterval();
        }
        return waitInterval;
    }
    
    public void setWaitInterval(long interval) {
        this.waitInterval = interval;
    }
    
    public long getWaitTimeout() {
        if (waitTimeout == -568522662) {
            return getWebDriver().getWaitTimeout();
        }
        return waitTimeout;
    }
    
    public void setWaitTimeout(long timeout) {
        this.waitTimeout = timeout;
    }
    
    public Waiter waiter() {
        return waiter(getWaitInterval(), getWaitTimeout());
    }
    
    public Waiter waiter(long timeout) {
        return waiter(getWaitInterval(), timeout);
    }
    
    public Waiter waiter(long interval, long timeout) {
        return new Waiter(interval, timeout);
    }

    /**
     * Only used by {@link com.iselsoft.easyium.Context#refreshMe}
     * @param locator
     * @return
     * @throws EasyiumException
     */
    protected WebElement findSeleniumElement(String locator) throws EasyiumException {
        By by = LocatorHelper.toBy(locator);
        try {
            try{
                return seleniumContext().findElement(by);
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                refreshMe();
                return seleniumContext().findElement(by);
            }
        } catch (org.openqa.selenium.InvalidSelectorException e) {
            throw new InvalidLocatorException(String.format("The value of locator <%s> is not a valid expression.", locator), this);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new NoSuchElementException(String.format("Cannot find element by <%s> under:", locator), this);
        } catch (org.openqa.selenium.WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public boolean hasChild(String locator) throws EasyiumException, InterruptedException {
        try {
            findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    public Element findElement(String locator) throws EasyiumException, InterruptedException {
        return findElement(locator, Identifier.id);
    }
    
    public Element findElement(String locator, Identifier identifier) throws EasyiumException, InterruptedException {
        By by = LocatorHelper.toBy(locator);
        try {
            try {
                return new DynamicElement(this, seleniumContext().findElement(by), locator, identifier);
            } catch (NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
                // Only Element can reach here
                ((Element) this).waitFor().exists();
                return new DynamicElement(this, seleniumContext().findElement(by), locator, identifier);
            }
        } catch (org.openqa.selenium.InvalidSelectorException e) {
            throw new InvalidLocatorException(String.format("The value of locator <%s> is not a valid expression.", locator), this);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new NoSuchElementException(String.format("Cannot find element by <%s> under:", locator), this);
        } catch (org.openqa.selenium.WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Only used by {@link com.iselsoft.easyium.Context#findElements}
     * @param locator
     * @return
     * @throws EasyiumException
     */
    protected List<WebElement> findSeleniumElements(String locator) throws EasyiumException, InterruptedException {
        By by = LocatorHelper.toBy(locator);
        try {
            try {
                return seleniumContext().findElements(by);
            } catch (NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
                // Only Element can reach here
                ((Element) this).waitFor().exists();
                return seleniumContext().findElements(by);
            }
        } catch (org.openqa.selenium.InvalidSelectorException e) {
            throw new InvalidLocatorException(String.format("The value of locator <%s> is not a valid expression.", locator) ,this);
        } catch (org.openqa.selenium.WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public List<Element> findElements(String locator) throws EasyiumException, InterruptedException {
        return findElements(locator, Identifier.id, 0);
    }

    public List<Element> findElements(String locator, int atLeast) throws EasyiumException, InterruptedException {
        return findElements(locator, Identifier.id, atLeast);
    }

    public List<Element> findElements(String locator, Identifier identifier) throws EasyiumException, InterruptedException {
        return findElements(locator, identifier, 0);
    }
    
    public List<Element> findElements(String locator, Identifier identifier, int atLeast) throws EasyiumException, InterruptedException {
        List<Element> elements = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        List<WebElement> seleniumElements = findSeleniumElements(locator);
        if (seleniumElements.size() >= atLeast) {
            for (WebElement seleniumElement : seleniumElements) {
                elements.add(new DynamicElement(this, seleniumElement, locator, identifier));
            }
            return elements;
        }
        
        while ((System.currentTimeMillis() - startTime) <= getWaitTimeout()) {
            Thread.sleep(getWaitInterval());
            seleniumElements = findSeleniumElements(locator);
            if (seleniumElements.size() >= atLeast) {
                for (WebElement seleniumElement : seleniumElements) {
                    elements.add(new DynamicElement(this, seleniumElement, locator, identifier));
                }
                return elements;
            }
        }

        throw new TimeoutException(String.format("Timed out waiting for the number of found elements by <%s> under:\n%s\nto be at least <%s>.",
                locator, this, atLeast));
    }
}
