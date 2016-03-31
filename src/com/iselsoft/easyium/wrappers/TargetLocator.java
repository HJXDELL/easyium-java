package com.iselsoft.easyium.wrappers;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.WebDriver;
import com.iselsoft.easyium.WebDriverType;
import org.openqa.selenium.Alert;
import org.openqa.selenium.ContextAware;

public class TargetLocator {
    protected final WebDriver webDriver;
    protected final org.openqa.selenium.WebDriver.TargetLocator targetLocator;
    
    
    public TargetLocator(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.targetLocator = webDriver.seleniumWebDriver().switchTo();
    }

    /**
     * Select a frame by its (zero-based) index. Selecting a frame by index is equivalent to the
     * JS expression window.frames[index] where "window" is the DOM window represented by the
     * current context. Once the frame has been selected, all subsequent calls on the WebDriver
     * interface are made to that frame.
     *
     * @param index (zero-based) index
     * @return This driver focused on the given frame
     * @throws org.openqa.selenium.NoSuchFrameException If the frame cannot be found
     */
    public WebDriver frame(int index) {
        targetLocator.frame(index);
        return webDriver;
    }

    /**
     * Select a frame by its name or ID. Frames located by matching name attributes are always given
     * precedence over those matched by ID.
     *
     * @param nameOrId the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt;
     *        element, or the (zero-based) index
     * @return This driver focused on the given frame
     * @throws org.openqa.selenium.NoSuchFrameException If the frame cannot be found
     */
    public WebDriver frame(String nameOrId) {
        targetLocator.frame(nameOrId);
        return webDriver;
    }

    /**
     * Select a frame using its previously located {@link Element}.
     *
     * @param frameElement The frame element to switch to.
     * @return This driver focused on the given frame.
     * @throws org.openqa.selenium.NoSuchFrameException If the given element is neither an IFRAME nor a FRAME element.
     * @throws org.openqa.selenium.StaleElementReferenceException If the WebElement has gone stale.
     * @see WebDriver#findElement(org.openqa.selenium.By)
     */
    public WebDriver frame(Element frameElement) {
        frameElement.waitFor().exists();
        targetLocator.frame(frameElement.seleniumElement());
        return webDriver;
    }

    /**
     * Change focus to the parent context. If the current context is the top level browsing context,
     * the context remains unchanged.
     *
     * @return This driver focused on the parent frame
     */
    public WebDriver parentFrame() {
        targetLocator.parentFrame();
        return webDriver;
    }

    /**
     * Switch the focus of future commands for this driver to the window with the given name/handle.
     *
     * @param nameOrHandle The name of the window or the handle as returned by
     *        {@link WebDriver#getWindowHandle()}
     * @return This driver focused on the given window
     * @throws org.openqa.selenium.NoSuchWindowException If the window cannot be found
     */
    public WebDriver window(String nameOrHandle) {
        targetLocator.window(nameOrHandle);
        return webDriver;
    }

    /**
     * Selects either the first frame on the page, or the main document when a page contains
     * iframes.
     *
     * @return This driver focused on the top window/first frame.
     */
    public WebDriver defaultContent() {
        targetLocator.defaultContent();
        return webDriver;
    }

    /**
     * Switches to the currently active modal dialog for this particular driver instance.
     *
     * @return A handle to the dialog.
     * @throws org.openqa.selenium.NoAlertPresentException If the dialog cannot be found
     */
    public Alert alert() {
        return targetLocator.alert();
    }
    
    public WebDriver context(String name) {
        webDriver.checkSupport(WebDriverType.MOBILE);
        
        ((ContextAware) webDriver.seleniumWebDriver()).context(name);
        return webDriver;
    }
}
