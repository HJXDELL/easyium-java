package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.EasyiumException;
import com.iselsoft.easyium.exceptions.NoSuchElementException;
import com.iselsoft.easyium.waiter.element.ElementWaitFor;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

public abstract class Element extends Context {
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
    protected SearchContext seleniumContext() {
        if (seleniumElement == null) {
            refresh();
        }
        return seleniumElement;
    }

    public WebElement seleniumElement() {
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

    public void click() {
        try {
            try {
                seleniumElement().click();
            } catch (NoSuchElementException | StaleElementReferenceException | ElementNotVisibleException e) {
                waitFor().visible();
                seleniumElement().click();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void submit() {
        try {
            try {
                seleniumElement().submit();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                seleniumElement().submit();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void sendKeys(CharSequence... keysToSend) {
        try {
            try {
                seleniumElement().sendKeys(keysToSend);
            } catch (NoSuchElementException | StaleElementReferenceException | ElementNotVisibleException e) {
                waitFor().visible();
                seleniumElement().sendKeys(keysToSend);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public void clear() {
        try {
            try {
                seleniumElement().clear();
            } catch (NoSuchElementException | StaleElementReferenceException | ElementNotVisibleException e) {
                waitFor().visible();
                seleniumElement().clear();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public String getTagName() {
        try {
            try {
                return seleniumElement().getTagName();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getTagName();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public String getAttribute(String name) {
        try {
            try {
                return seleniumElement().getAttribute(name);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getAttribute(name);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public boolean isSelected() {
        try {
            try {
                return seleniumElement().isSelected();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                return seleniumElement().isSelected();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public boolean isEnabled() {
        try {
            try {
                return seleniumElement().isEnabled();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                return seleniumElement().isEnabled();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public String getText() {
        try {
            try {
                return seleniumElement().getText();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getText();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public Point getLocation() {
        try {
            try {
                return seleniumElement().getLocation();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getLocation();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public Dimension getSize() {
        try {
            try {
                return seleniumElement().getSize();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getSize();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public Rectangle getRect() {
        try {
            try {
                return seleniumElement().getRect();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getRect();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public String getCssValue(String propertyName) {
        try {
            try {
                return seleniumElement().getCssValue(propertyName);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getCssValue(propertyName);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public Coordinates getCoordinates() {
        checkSupport(WebDriverType.MOBILE);
        
        try {
            try {
                return ((Locatable) seleniumElement()).getCoordinates();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return ((Locatable) seleniumElement()).getCoordinates();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public void tap() {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                getWebDriver().createTouchAction().tap(seleniumElement()).perform();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().createTouchAction().tap(seleniumElement()).perform();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public void longPress() {
        checkSupport(WebDriverType.MOBILE);
        
        try {
            try {
                getWebDriver().createTouchAction().longPress(seleniumElement()).perform();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().createTouchAction().longPress(seleniumElement()).perform();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void longPress(int duration) {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                getWebDriver().createTouchAction().longPress(seleniumElement(), duration).perform();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().createTouchAction().longPress(seleniumElement(), duration).perform();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public void scroll(SwipeElementDirection direction, int duration) {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                ((TouchableElement) seleniumElement()).swipe(direction, duration);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                ((TouchableElement) seleniumElement()).swipe(direction, duration);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public void pinch() {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                ((TouchableElement) seleniumElement()).pinch();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                ((TouchableElement) seleniumElement()).pinch();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void zoom() {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                ((TouchableElement) seleniumElement()).zoom();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                ((TouchableElement) seleniumElement()).zoom();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    public void setValue(String value) {
        checkSupport(WebDriverType.IOS);

        try {
            try {
                ((IOSElement) seleniumElement()).setValue(value);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                ((IOSElement) seleniumElement()).setValue(value);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void replaceValue(String value) {
        checkSupport(WebDriverType.ANDROID);

        try {
            try {
                ((AndroidElement) seleniumElement()).replaceValue(value);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                ((AndroidElement) seleniumElement()).replaceValue(value);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public boolean isDisplayed() {
        try {
            try {
                return seleniumElement().isDisplayed();
            } catch (StaleElementReferenceException e) {
                refresh();
                return seleniumElement().isDisplayed();
            }
        } catch (NoSuchElementException e) {
            return false;
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public boolean exists() {
        try {
            try {
                seleniumElement().isDisplayed();
                return true;
            } catch (StaleElementReferenceException e) {
                refresh();
                return true;
            }
        } catch (NoSuchElementException e) {
            return false;
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
}
