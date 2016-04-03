package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.*;
import com.iselsoft.easyium.exceptions.NoSuchElementException;
import com.iselsoft.easyium.exceptions.TimeoutException;
import com.iselsoft.easyium.exceptions.UnsupportedOperationException;
import com.iselsoft.easyium.waiter.Waiter;
import org.openqa.selenium.*;

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

    protected abstract SearchContext seleniumContext();

    protected abstract void refresh();

    public abstract void persist();

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

    public <X> X getScreenshotAs(OutputType<X> target) {
        try {
            try {
                return ((TakesScreenshot) seleniumContext()).getScreenshotAs(target);
            } catch (NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
                // Only Element can reach here
                ((Element) this).waitFor().exists();
                return ((TakesScreenshot) seleniumContext()).getScreenshotAs(target);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    protected void checkSupport(WebDriverType... webDriverTypes) {
        checkSupport(new ArrayList<WebDriverType>(), webDriverTypes);
    }

    protected void checkSupport(List<WebDriverType> webDriverTypeGroup, WebDriverType... webDriverTypes) {
        WebDriverType currentWebDriverType = getWebDriverType();
        for (WebDriverType webDriverType : webDriverTypeGroup) {
            if (webDriverType == currentWebDriverType) {
                return;
            }
        }
        for (WebDriverType webDriverType : webDriverTypes) {
            if (webDriverType == currentWebDriverType) {
                return;
            }
        }
        throw new UnsupportedOperationException(String.format("Operation is not supported by web driver [%s].", currentWebDriverType));
    }

    /**
     * Only used by {@link Context#refresh()}
     *
     * @param locator
     * @return
     */
    protected WebElement findSeleniumElement(String locator) {
        By by = LocatorHelper.toBy(locator);
        try {
            try {
                return seleniumContext().findElement(by);
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                refresh();
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

    public boolean hasChild(String locator) {
        try {
            findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Element findElement(String locator) {
        return findElement(locator, Identifier.id);
    }

    public Element findElement(String locator, Identifier identifier) {
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
     * Only used by {@link Context#findElements}
     *
     * @param locator
     * @return
     */
    protected List<WebElement> findSeleniumElements(String locator) {
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
            throw new InvalidLocatorException(String.format("The value of locator <%s> is not a valid expression.", locator), this);
        } catch (org.openqa.selenium.WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public List<Element> findElements(String locator) {
        return findElements(locator, Identifier.id, 0);
    }

    public List<Element> findElements(String locator, int atLeast) {
        return findElements(locator, Identifier.id, atLeast);
    }

    public List<Element> findElements(String locator, Identifier identifier) {
        return findElements(locator, identifier, 0);
    }

    public List<Element> findElements(String locator, Identifier identifier, int atLeast) {
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
            try {
                Thread.sleep(getWaitInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
