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
    public static final long UNSET_WAIT_TIME = Long.MIN_VALUE;
    
    private long waitInterval;
    private long waitTimeout;

    protected Context() {
        waitInterval = UNSET_WAIT_TIME;
        waitTimeout = UNSET_WAIT_TIME;
    }

    /**
     * @return the {@link WebDriver} of this context.
     */
    public abstract WebDriver getWebDriver();

    /**
     * @return the {@link WebDriverType} of this context's web driver
     */
    public abstract WebDriverType getWebDriverType();

    protected abstract SearchContext seleniumContext();

    protected abstract void refresh();

    /**
     * persist this context.
     * 
     * @see DynamicElement#persist()
     */
    public abstract void persist();

    /**
     * Get the wait interval of this context. If the wait interval for element is not set, return the driver's wait interval.
     *
     * @return the wait interval
     */
    public long getWaitInterval() {
        if (waitInterval == UNSET_WAIT_TIME) {
            return getWebDriver().getWaitInterval();
        }
        return waitInterval;
    }

    /**
     * Set the wait interval of this context.
     *
     * @param interval the new wait interval in milliseconds
     */
    public void setWaitInterval(long interval) {
        this.waitInterval = interval;
    }

    /**
     * Get the wait timeout of this context. If the wait timeout for element is not set, return the driver's wait timeout.
     *
     * @return the wait timeout
     */
    public long getWaitTimeout() {
        if (waitTimeout == UNSET_WAIT_TIME) {
            return getWebDriver().getWaitTimeout();
        }
        return waitTimeout;
    }

    /**
     * Set the wait timeout of this context.
     *
     * @param timeout the new wait timeout in milliseconds
     */
    public void setWaitTimeout(long timeout) {
        this.waitTimeout = timeout;
    }

    /**
     * Get a Waiter instance with this context's wait interval and timeout.
     *
     * @return Waiter instance
     */
    public Waiter waiter() {
        return waiter(getWaitInterval(), getWaitTimeout());
    }

    /**
     * Get a Waiter instance with this context's wait interval.
     *
     * @param timeout the wait timeout in milliseconds
     * @return Waiter instance
     */
    public Waiter waiter(long timeout) {
        return waiter(getWaitInterval(), timeout);
    }

    /**
     * Get a Waiter instance.
     *
     * @param interval the wait interval in milliseconds
     * @param timeout  the wait timeout in milliseconds
     * @return Waiter instance
     */
    public Waiter waiter(long interval, long timeout) {
        return new Waiter(interval, timeout);
    }

    /**
     * Capture the screenshot and store it in the specified location.
     * <p/>
     * <p>For WebDriver, this makes a best effort
     * depending on the browser to return the following in order of preference:
     * <ul>
     * <li>Entire page</li>
     * <li>Current window</li>
     * <li>Visible portion of the current frame</li>
     * <li>The screenshot of the entire display containing the browser</li>
     * </ul>
     * <p/>
     * <p>For Element, this makes a best effort
     * depending on the browser to return the following in order of preference:
     * - The entire content of the HTML element
     * - The visible portion of the HTML element
     *
     * @param <X>    Return type for getScreenshotAs.
     * @param target target type, @see OutputType
     * @return Object in which is stored information about the screenshot.
     * @throws EasyiumException on failure.
     * @see OutputType
     */
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

    /**
     * Check whether the webdriver type match requires.
     *
     * @param webDriverTypes webdriver types
     */
    public void checkSupport(WebDriverType... webDriverTypes) {
        checkSupport(new ArrayList<WebDriverType>(), webDriverTypes);
    }

    /**
     * Check whether the webdriver type match requires.
     *
     * @param webDriverTypeGroup webdriver type group
     * @param webDriverTypes     webdriver types
     */
    public void checkSupport(List<WebDriverType> webDriverTypeGroup, WebDriverType... webDriverTypes) {
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
     * Only used by {@link #refresh()}.
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

    /**
     * @param locator the locator (relative to this context) of the child element. @see LocatorHelper
     * @return whether this context has a child element.
     * @see LocatorHelper
     */
    public boolean hasChild(String locator) {
        try {
            findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Find a DynamicElement with {@link Identifier#id} as identifier.
     * 
     * @param locator the locator (relative to this context) of the element to be found. @see LocatorHelper
     * @return found DynamicElement
     * @see LocatorHelper
     * @see #findElement(String, Identifier)
     */
    public Element findElement(String locator) {
        return findElement(locator, Identifier.id);
    }

    /**
     * Find a DynamicElement under this context immediately.
     *
     * @param locator the locator (relative to this context) of the element to be found. @see LocatorHelper
     * @param identifier the identifier to identify the locator of the found DynamicElement @see Identifier
     * @return found DynamicElement
     * @see LocatorHelper
     * @see Identifier
     */
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
     * Only used by {@link #findElements}.
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

    /**
     * Find DynamicElement list with 0 as atLeast and {@link Identifier#id} as identifier.
     *
     * @param locator the locator (relative to this context) of the elements to be found. @see LocatorHelper
     * @return found DynamicElement List
     * @see LocatorHelper
     * @see #findElements(String, Identifier, int)
     */
    public List<Element> findElements(String locator) {
        return findElements(locator, Identifier.id, 0);
    }

    /**
     * Find DynamicElement list with {@link Identifier#id} as identifier.
     *
     * @param locator the locator (relative to this context) of the elements to be found. @see LocatorHelper
     * @param atLeast end finding elements when the number of found elements is at least the given number.
     * @return found DynamicElement List
     * @see LocatorHelper
     * @see #findElements(String, Identifier, int)
     */
    public List<Element> findElements(String locator, int atLeast) {
        return findElements(locator, Identifier.id, atLeast);
    }

    /**
     * Find DynamicElement list with 0 as atLeast.
     *
     * @param locator the locator (relative to this context) of the elements to be found. @see LocatorHelper
     * @param identifier the identifier to identify the locator of the found DynamicElements @see Identifier
     * @return found DynamicElement List
     * @see LocatorHelper
     * @see Identifier
     * @see #findElements(String, Identifier, int)
     */
    public List<Element> findElements(String locator, Identifier identifier) {
        return findElements(locator, identifier, 0);
    }

    /**
     * Find DynamicElement list under this context immediately.
     *
     * @param locator the locator (relative to this context) of the elements to be found. @see LocatorHelper
     * @param identifier the identifier to identify the locator of the found DynamicElements @see Identifier
     * @param atLeast end finding elements when the number of found elements is at least the given number.
     * @return found DynamicElement List
     * @see LocatorHelper
     * @see Identifier
     */
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
                Thread.currentThread().interrupt();
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
