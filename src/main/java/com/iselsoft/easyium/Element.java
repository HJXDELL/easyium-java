package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.EasyiumException;
import com.iselsoft.easyium.exceptions.NoSuchElementException;
import com.iselsoft.easyium.waiter.element.ElementWaitFor;
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

    /**
     * @return parent context of this element
     */
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

    /**
     * Get selenium {@link org.openqa.selenium.WebElement} for further operations.
     *
     * @return selenium {@link org.openqa.selenium.WebElement} instance
     */
    public WebElement seleniumElement() {
        if (seleniumElement == null) {
            refresh();
        }
        return seleniumElement;
    }

    /**
     * Get a ElementWaitFor instance with this element's wait interval and timeout.
     *
     * @return ElementWaitFor instance
     */
    public ElementWaitFor waitFor() {
        return waitFor(getWaitInterval(), getWaitTimeout());
    }

    /**
     * Get a ElementWaitFor instance with this element's wait interval.
     *
     * @param timeout the wait timeout in milliseconds
     * @return ElementWaitFor instance
     */
    public ElementWaitFor waitFor(long timeout) {
        return waitFor(getWaitInterval(), timeout);
    }

    /**
     * Get a ElementWaitFor instance.
     *
     * @param interval the wait interval in milliseconds
     * @param timeout  the wait timeout in milliseconds
     * @return ElementWaitFor instance
     */
    public ElementWaitFor waitFor(long interval, long timeout) {
        return new ElementWaitFor(this, interval, timeout);
    }

    /**
     * Focus this element.
     */
    public void focus() {
        try {
            try {
                getWebDriver().executeScript("arguments[0].focus()", this);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().executeScript("arguments[0].focus()", this);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }
    
    /**
     * Removes keyboard focus from this element.
     */
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

    /**
     * Click this element. If this causes a new page to load, you
     * should discard all references to this element and any further
     * operations performed on this element will throw a
     * StaleElementReferenceException.
     * <p>
     * Note that if click() is done by sending a native event (which is
     * the default on most browsers/platforms) then the method will
     * _not_ wait for the next page to load and the caller should verify
     * that themselves.
     * <p>
     * There are some preconditions for an element to be clicked. The
     * element must be visible and it must have a height and width
     * greater then 0.
     */
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

    /**
     * Performs a double-click at middle of this element.
     */
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

    /**
     * Performs a context-click at middle of this element. First performs a mouseMove
     * to the location of the element.
     */
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

    /**
     * If this current element is a form, or an element within a form, then this will be submitted to
     * the remote server. If this causes the current page to change, then this method will block until
     * the new page is loaded.
     */
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

    /**
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend character sequence to send to the element
     */
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

    /**
     * If this element is a text entry element, this will clear the value. Has no effect on other
     * elements. Text entry elements are INPUT and TEXTAREA elements.
     * <p>
     * Note that the events fired by this event may not be as you'd expect.  In particular, we don't
     * fire any keyboard or mouse events.  If you want to ensure keyboard events are fired, consider
     * using something like {@link #sendKeys(CharSequence...)} with the backspace key.  To ensure
     * you get a change event, consider following with a call to {@link #sendKeys(CharSequence...)}
     * with the tab key.
     */
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

    /**
     * Get the tag name of this element. <b>Not</b> the value of the name attribute: will return
     * <code>"input"</code> for the element <code>&lt;input name="foo" /&gt;</code>.
     *
     * @return The tag name of this element.
     */
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

    /**
     * Get the value of a the given attribute of this element. Will return the current value, even if
     * this has been modified after the page has been loaded. More exactly, this method will return
     * the value of the given attribute, unless that attribute is not present, in which case the value
     * of the property with the same name is returned (for example for the "value" property of a
     * textarea element). If neither value is set, null is returned. The "style" attribute is
     * converted as best can be to a text representation with a trailing semi-colon. The following are
     * deemed to be "boolean" attributes, and will return either "true" or null:
     * <p>
     * async, autofocus, autoplay, checked, compact, complete, controls, declare, defaultchecked,
     * defaultselected, defer, disabled, draggable, ended, formnovalidate, hidden, indeterminate,
     * iscontenteditable, ismap, itemscope, loop, multiple, muted, nohref, noresize, noshade,
     * novalidate, nowrap, open, paused, pubdate, readonly, required, reversed, scoped, seamless,
     * seeking, selected, spellcheck, truespeed, willvalidate
     * <p>
     * Finally, the following commonly mis-capitalized attribute/property names are evaluated as
     * expected:
     * <ul>
     * <li>"class"
     * <li>"readonly"
     * </ul>
     *
     * @param name The name of the attribute.
     * @return The attribute/property's current value or null if the value is not set.
     */
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

    /**
     * Gets the value of this element.
     * Can be used to get the text of a text entry element.
     * Text entry elements are INPUT and TEXTAREA elements.
     *
     * @return the value of this element
     */
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

    /**
     * Get the visible (i.e. not hidden by CSS) innerText of this element, including sub-elements,
     * without any leading or trailing whitespace.
     *
     * @return The innerText of this element.
     */
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

    /**
     * Get content of the text node in this element.
     * f the text_node_index refers to a non-text node or be out of bounds, an exception will be thrown.
     *
     * @param textNodeIndex index of text node in this element
     * @return the content of the text node in this element.
     */
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

    /**
     * Set the selection range for text in this element.
     *
     * @param startIndex start position
     * @param endIndex   end position
     */
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

    /**
     * @return the inner html of this element.
     */
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

    /**
     * Do mouse over this element.
     */
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

    /**
     * Do mouse out this element.
     */
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

    /**
     * Where on the page is the top left-hand corner of the rendered element?
     *
     * @return A point, containing the location of the top left-hand corner of the element
     */
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

    /**
     * What is the width and height of the rendered element?
     *
     * @return The size of the element on the page.
     */
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

    /**
     * @return The location and size of the rendered element
     */
    public Rectangle getRect() {
        try {
            try {
                if (getWebDriverType() == WebDriverType.FIREFOX) {
                    return seleniumElement().getRect();
                } else {
                    return new Rectangle(seleniumElement().getLocation(), seleniumElement().getSize());
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                if (getWebDriverType() == WebDriverType.FIREFOX) {
                    return seleniumElement().getRect();
                } else {
                    return new Rectangle(seleniumElement().getLocation(), seleniumElement().getSize());
                }
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * @return the {@link Coordinates} of this element
     */
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

    /**
     * @return the location for the center of this element
     */
    public Point getCenter() {
        Rectangle rect = getRect();
        return new Point(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
    }

    /**
     * Get the value of a given CSS property.
     * Color values should be returned as rgba strings, so,
     * for example if the "background-color" property is set as "green" in the
     * HTML source, the returned value will be "rgba(0, 255, 0, 1)".
     * <p>
     * Note that shorthand CSS properties (e.g. background, font, border, border-top, margin,
     * margin-top, padding, padding-top, list-style, outline, pause, cue) are not returned,
     * in accordance with the
     * <a href="http://www.w3.org/TR/DOM-Level-2-Style/css.html#CSS-CSSStyleDeclaration">DOM CSS2 specification</a>
     * - you should directly access the longhand properties (e.g. background-color) to access the
     * desired values.
     *
     * @param propertyName the css property name of the element
     * @return The current, computed value of the property.
     */
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

    /**
     * Determine whether or not this element is selected or not. This operation only applies to input
     * elements such as checkboxes, options in a select and radio buttons.
     *
     * @return True if the element is currently selected or checked, false otherwise.
     */
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

    /**
     * Is the element currently enabled or not? This will generally return true for everything but
     * disabled input elements.
     *
     * @return True if the element is enabled, false otherwise.
     */
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

    /**
     * Drag and drop to target offset.
     *
     * @param xOffset X offset to drop
     * @param yOffset Y offset to drop
     */
    public void dragAndDropByOffset(int xOffset, int yOffset) {
        try {
            try {
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement(), 1000).moveTo(xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveByOffset(xOffset, yOffset).release().perform();
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement(), 1000).moveTo(xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveByOffset(xOffset, yOffset).release().perform();
                }
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Drag and drop to target element.
     *
     * @param targetElement the target element to drop
     */
    public void dragAndDropTo(Element targetElement) {
        try {
            try {
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement(), 1000).moveTo(targetElement.seleniumElement()).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement()).release().perform();
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                targetElement.waitFor().visible();
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement(), 1000).moveTo(targetElement.seleniumElement()).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement()).release().perform();
                }
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Drag and drop to target element with offset.
     * The origin is at the top-left corner of web driver and offsets are relative to the top-left corner of the target element.
     *
     * @param targetElement the target element to drop
     * @param xOffset       X offset to drop
     * @param yOffset       Y offset to drop
     */
    public void dragAndDropToWithOffset(Element targetElement, int xOffset, int yOffset) {
        try {
            try {
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement(), 1000).moveTo(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                targetElement.waitFor().visible();
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    getWebDriver().createTouchAction().longPress(seleniumElement(), 1000).moveTo(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                } else {
                    getWebDriver().createActions().clickAndHold(seleniumElement()).moveToElement(targetElement.seleniumElement(), xOffset, yOffset).release().perform();
                }
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Tap the center of this element.
     * <p><b>Supported by MOBILE</b></p>
     */
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

    /**
     * Double tap the center of this element.
     * <p><b>Supported by MOBILE</b></p>
     */
    public void doubleTap() {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                HashMap<String, String> tapParams = new HashMap<>();
                tapParams.put("tapCount", "2");
                tapParams.put("duration", "");
                tapParams.put("element", ((HasIdentity) seleniumElement()).getId());
                getWebDriver().executeScript("mobile: tap", tapParams);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                HashMap<String, String> tapParams = new HashMap<>();
                tapParams.put("tapCount", "2");
                tapParams.put("duration", "");
                tapParams.put("element", ((HasIdentity) seleniumElement()).getId());
                getWebDriver().executeScript("mobile: tap", tapParams);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Press and hold the at the center of this element until the contextmenu event has fired.
     * <p><b>Supported by MOBILE</b></p>
     */
    public void longPress() {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                getWebDriver().createTouchAction().longPress(seleniumElement()).release().perform();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().createTouchAction().longPress(seleniumElement()).release().perform();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Press and hold the at the center of this element.
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param duration of the long-press, in milliseconds
     */
    public void longPress(int duration) {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                getWebDriver().createTouchAction().longPress(seleniumElement(), duration).release().perform();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                getWebDriver().createTouchAction().longPress(seleniumElement(), duration).release().perform();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Scrolls to direction in this element.
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param direction the direction to scroll
     */
    public void scroll(ScrollDirection direction) {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                HashMap<String, String> scrollParams = new HashMap<>();
                scrollParams.put("direction", direction.getValue());
                scrollParams.put("element", ((HasIdentity) seleniumElement()).getId());
                getWebDriver().executeScript("mobile: scroll", scrollParams);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().visible();
                HashMap<String, String> scrollParams = new HashMap<>();
                scrollParams.put("direction", direction.getValue());
                scrollParams.put("element", ((HasIdentity) seleniumElement()).getId());
                getWebDriver().executeScript("mobile: scroll", scrollParams);
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Scrolls from this element to another.
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param targetElement the target element to be scrolled to
     */
    public void scrollTo(Element targetElement) {
        checkSupport(WebDriverType.MOBILE);

        try {
            try {
                getWebDriver().createTouchAction().press(seleniumElement()).moveTo(targetElement.seleniumElement()).release().perform();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                targetElement.waitFor().exists();
                getWebDriver().createTouchAction().press(seleniumElement()).moveTo(targetElement.seleniumElement()).release().perform();
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Scrolls this element into view.
     */
    public void scrollIntoView() {
        try {
            try {
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    HashMap<String, String> scrollParams = new HashMap<>();
                    scrollParams.put("element", ((HasIdentity) seleniumElement()).getId());
                    getWebDriver().executeScript("mobile: scrollTo", scrollParams);
                } else {
                    getWebDriver().executeScript("arguments[0].scrollIntoView();", this);
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                waitFor().exists();
                if (WebDriverType.MOBILE.contains(getWebDriverType())) {
                    HashMap<String, String> scrollParams = new HashMap<>();
                    scrollParams.put("element", ((HasIdentity) seleniumElement()).getId());
                    getWebDriver().executeScript("mobile: scrollTo", scrollParams);
                } else {
                    getWebDriver().executeScript("arguments[0].scrollIntoView();", this);
                }
            }
        } catch (WebDriverException e) {
            throw new EasyiumException(e.getMessage(), this);
        }
    }

    /**
     * Convenience method for pinching this element.
     * "pinching" refers to the action of two appendages pressing the screen and sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element, if this would happen to place one of them
     * off the screen, appium with return an outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     * <p><b>Supported by MOBILE</b></p>
     */
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

    /**
     * Convenience method for "zooming in" on this element.
     * "zooming in" refers to the action of two appendages pressing the screen and sliding away from each other.
     * NOTE:
     * This convenience method slides touches away from the element, if this would happen to place one of them
     * off the screen, appium will return an outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     * <p><b>Supported by MOBILE</b></p>
     */
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

    /**
     * Set the value on this element in the application
     * <p><b>Supported by IOS</b></p>
     *
     * @param value the value to set
     */
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

    /**
     * Replace the value on this element in the application
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param value the value to replace
     */
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

    /**
     * @return whether this element is displayed or not.
     */
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

    /**
     * @return whether this element is existing or not.
     */
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
