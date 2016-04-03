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
import org.openqa.selenium.internal.HasIdentity;
import org.openqa.selenium.internal.Locatable;

import java.util.HashMap;

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

    public void blur() {
        try {
            try {
                getWebDriver().executeScript("arguments[0].blur()", this);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().executeScript("arguments[0].blur()", this);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
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

    public void doubleClick() {
        try {
            try {
                getWebDriver().createActions().doubleClick(seleniumElement()).perform();
            } catch (NoSuchElementException | StaleElementReferenceException | ElementNotVisibleException e) {
                waitFor().visible();
                getWebDriver().createActions().doubleClick(seleniumElement()).perform();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void contextClick() {
        try {
            try {
                getWebDriver().createActions().contextClick(seleniumElement()).perform();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().createActions().contextClick(seleniumElement()).perform();
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

    public String getValue() {
        try {
            try {
                return seleniumElement().getAttribute("value");
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return seleniumElement().getAttribute("value");
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

    public String getTextNodeContent(int textNodeIndex) {
        String content;

        try {
            try {
                content = (String) getWebDriver().executeScript(String.format("return arguments[0].childNodes[%s].nodeValue", textNodeIndex), this);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                content = (String) getWebDriver().executeScript(String.format("return arguments[0].childNodes[%s].nodeValue", textNodeIndex), this);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }

        if (content == null) {
            throw new EasyiumException("Cannot get text content of a non-text node in element:", this);
        }
        return content;
    }

    public void setSelectionRange(int startIndex, int endIndex) {
        String script = "function getTextNodesIn(node) {\n" +
                "                var textNodes = [];\n" +
                "                if (node.nodeType == 3) {\n" +
                "                    textNodes.push(node);\n" +
                "                } else {\n" +
                "                    var children = node.childNodes;\n" +
                "                    for (var i = 0, len = children.length; i < len; ++i) {\n" +
                "                        textNodes.push.apply(textNodes, getTextNodesIn(children[i]));\n" +
                "                    }\n" +
                "                }\n" +
                "                return textNodes;\n" +
                "            }\n" +
                "\n" +
                "            function setSelectionRange(el, start, end) {\n" +
                "                if (el.tagName == 'INPUT' || el.tagName == 'TEXTAREA'){\n" +
                "                    if(el.createTextRange){\n" +
                "                        var Range=el.createTextRange();\n" +
                "                        Range.collapse();\n" +
                "                        Range.moveEnd('character',end);\n" +
                "                        Range.moveStart('character',start);\n" +
                "                        Range.select();\n" +
                "                    }else if(el.setSelectionRange){\n" +
                "                        el.focus();\n" +
                "                        el.setSelectionRange(start,end);\n" +
                "                    }\n" +
                "                } else {\n" +
                "            if (document.createRange && window.getSelection) {\n" +
                "                        var range = document.createRange();\n" +
                "                        range.selectNodeContents(el);\n" +
                "                        var textNodes = getTextNodesIn(el);\n" +
                "                        var foundStart = false;\n" +
                "                        var charCount = 0, endCharCount;\n" +
                "\n" +
                "                        for (var i = 0, textNode; textNode = textNodes[i++]; ) {\n" +
                "                            endCharCount = charCount + textNode.length;\n" +
                "                            if (!foundStart && start >= charCount\n" +
                "                                    && (start < endCharCount ||\n" +
                "                                    (start == endCharCount && i < textNodes.length))) {\n" +
                "                                range.setStart(textNode, start - charCount);\n" +
                "                                foundStart = true;\n" +
                "                            }\n" +
                "                            if (foundStart && end <= endCharCount) {\n" +
                "                                range.setEnd(textNode, end - charCount);\n" +
                "                                break;\n" +
                "                            }\n" +
                "                            charCount = endCharCount;\n" +
                "                        }\n" +
                "\n" +
                "                        var sel = window.getSelection();\n" +
                "                        sel.removeAllRanges();\n" +
                "                        sel.addRange(range);\n" +
                "                    } else if (document.selection && document.body.createTextRange) {\n" +
                "                        var textRange = document.body.createTextRange();\n" +
                "                        textRange.moveToElementText(el);\n" +
                "                        textRange.collapse(true);\n" +
                "                        textRange.moveEnd('character', end);\n" +
                "                        textRange.moveStart('character', start);\n" +
                "                        textRange.select();\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "\n" +
                "            setSelectionRange(arguments[0], %s, %s);";

        try {
            try {
                getWebDriver().executeScript(String.format(script, startIndex, endIndex), this);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().executeScript(String.format(script, startIndex, endIndex), this);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public String getInnerHTML() {
        try {
            try {
                return (String) getWebDriver().executeScript("return arguments[0].innerHTML", this);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                return (String) getWebDriver().executeScript("return arguments[0].innerHTML", this);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void mouseOver() {
        checkSupport(WebDriverType.BROWSER);

        String script = "var mouseoverEventObj = null;\n" +
                "            if (typeof window.Event == \"function\") {\n" +
                "                mouseoverEventObj = new MouseEvent('mouseover', {'bubbles': true, 'cancelable': true});\n" +
                "            } else {\n" +
                "                mouseoverEventObj = document.createEvent(\"MouseEvents\");\n" +
                "                mouseoverEventObj.initMouseEvent(\"mouseover\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);\n" +
                "            }\n" +
                "            arguments[0].dispatchEvent(mouseoverEventObj);";

        try {
            try {
                getWebDriver().executeScript(script, this);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                getWebDriver().executeScript(script, this);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void mouseOut() {
        checkSupport(WebDriverType.BROWSER);

        String script = "var mouseoutEventObj = null;\n" +
                "            if (typeof window.Event == \"function\") {\n" +
                "                mouseoutEventObj = new MouseEvent('mouseout', {'bubbles': true, 'cancelable': true});\n" +
                "            } else {\n" +
                "                mouseoutEventObj = document.createEvent(\"MouseEvents\");\n" +
                "                mouseoutEventObj.initMouseEvent(\"mouseout\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);\n" +
                "            }\n" +
                "            arguments[0].dispatchEvent(mouseoutEventObj);";

        try {
            try {
                getWebDriver().executeScript(script, this);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                getWebDriver().executeScript(script, this);
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

    public Coordinates getCoordinates() {
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

    public Point getCenter() {
        Point upperLeft = getLocation();
        Dimension dimensions = getSize();
        return new Point(upperLeft.getX() + dimensions.getWidth() / 2, upperLeft.getY() + dimensions.getHeight() / 2);
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

    public void dragAndDropByOffset(int xOffset, int yOffset) {
        try {
            try {
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement()).moveTo(xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveByOffset(xOffset, yOffset).release().perform();
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement()).moveTo(xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveByOffset(xOffset, yOffset).release().perform();
                }
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void dragAndDropTo(Element targetElement) {
        try {
            try {
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement()).moveTo(targetElement.seleniumElement()).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement()).release().perform();
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                targetElement.waitFor().visible();
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement()).moveTo(targetElement.seleniumElement()).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement()).release().perform();
                }
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    public void dragAndDropToWithOffset(Element targetElement, int xOffset, int yOffset) {
        try {
            try {
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement()).moveTo(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                targetElement.waitFor().visible();
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement()).moveTo(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                }
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

    public void scroll(ScrollDirection direction) {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                HashMap<String, String> scrollParam = new HashMap<>();
                scrollParam.put("direction", direction.getValue());
                scrollParam.put("element", ((HasIdentity) seleniumElement()).getId());
                getWebDriver().executeScript("mobile: scroll", scrollParam);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                HashMap<String, String> scrollParam = new HashMap<>();
                scrollParam.put("direction", direction.getValue());
                scrollParam.put("element", ((HasIdentity) seleniumElement()).getId());
                getWebDriver().executeScript("mobile: scroll", scrollParam);
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
