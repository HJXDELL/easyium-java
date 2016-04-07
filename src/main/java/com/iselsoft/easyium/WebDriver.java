package com.iselsoft.easyium;

import com.google.gson.JsonObject;
import com.iselsoft.easyium.exceptions.*;
import com.iselsoft.easyium.waiter.webdriver.WebDriverWaitFor;
import io.appium.java_client.*;
import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.android.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDeviceActionShortcuts;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Killable;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class WebDriver extends Context {
    public final static long DEFAULT_WAIT_INTERVAL = 1000;
    public final static long DEFAULT_WAIT_TIMEOUT = 30000;
    public final static long DEFAULT_PAGE_LOAD_TIMEOUT = 30000;
    public final static long DEFAULT_SCRIPT_TIMEOUT = 30000;

    protected final org.openqa.selenium.WebDriver seleniumWebDriver;

    protected WebDriver(org.openqa.selenium.WebDriver seleniumWebDriver) {
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

    public Actions createActions() {
        return new Actions(seleniumWebDriver());
    }

    public TouchAction createTouchAction() {
        checkSupport(WebDriverType.MOBILE);

        return new TouchAction((MobileDriver) seleniumWebDriver());
    }

    public MultiTouchAction createMultiTouchAction() {
        checkSupport(WebDriverType.MOBILE);

        return new MultiTouchAction((MobileDriver) seleniumWebDriver());
    }

    /**
     * Load a new web page in the current browser window. This is done using an HTTP GET operation,
     * and the method will block until the load is complete. This will follow redirects issued either
     * by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect
     * "rest" for any duration of time, it is best to wait until this timeout is over, since should
     * the underlying page change whilst your test is executing the results of future calls against
     * this interface will be against the freshly loaded page. Synonym for
     * {@link Navigation#to(String)}.
     *
     * @param url The URL to load. It is best to use a fully qualified URL
     */
    public void get(String url) {
        seleniumWebDriver().get(url);
    }

    /**
     * Alias for {@link #get(String)}
     */
    public void open(String url) {
        get(url);
    }

    /**
     * Get a string representing the current URL that the browser is looking at.
     *
     * @return The URL of the page currently loaded in the browser
     */
    public String getCurrentUrl() {
        return seleniumWebDriver().getCurrentUrl();
    }

    /**
     * The title of the current page.
     *
     * @return The title of the current page, with leading and trailing whitespace stripped, or null
     * if one is not already set
     */
    public String getTitle() {
        return seleniumWebDriver().getTitle();
    }

    /**
     * Get the source of the last loaded page. If the page has been modified after loading (for
     * example, by Javascript) there is no guarantee that the returned text is that of the modified
     * page. Please consult the documentation of the particular driver being used to determine whether
     * the returned text reflects the current state of the page or the text last sent by the web
     * server. The page source returned is a representation of the underlying DOM: do not expect it to
     * be formatted or escaped in the same way as the response sent from the web server. Think of it as
     * an artist's impression.
     *
     * @return The source of the current page
     */
    public String getPageSource() {
        return seleniumWebDriver().getPageSource();
    }

    /**
     * Close the current window, quitting the browser if it's the last window currently open.
     */
    public void closeCurrentWindow() {
        seleniumWebDriver().close();
    }

    /**
     * Close the specified window by name or handle.
     *
     * @param nameOrHandle The name of the window or the handle as returned by
     *                     {@link #getWindowHandles()}
     */
    public void closeWindow(String nameOrHandle) {
        if (nameOrHandle.equals(getCurrentWindowHandle())) {
            closeCurrentWindow();
        } else {
            String currentWindowHandle = getCurrentWindowHandle();
            switchTo().window(nameOrHandle);
            closeCurrentWindow();
            switchTo().window(currentWindowHandle);
        }
    }

    /**
     * Return an opaque handle to this window that uniquely identifies it within this driver instance.
     * This can be used to switch to this window at a later date
     *
     * @return the current window handle
     */
    public String getCurrentWindowHandle() {
        return seleniumWebDriver().getWindowHandle();
    }

    /**
     * Return a set of window handles which can be used to iterate over all open windows of this
     * WebDriver instance.
     *
     * @return A set of window handles which can be used to iterate over all open windows.
     */
    public Set<String> getWindowHandles() {
        return seleniumWebDriver().getWindowHandles();
    }

    /**
     * Gets the Option interface
     *
     * @return An option interface
     * @see Options
     */
    public Options manage() {
        return seleniumWebDriver().manage();
    }

    /**
     * Send future commands to a different frame, window, alert, context.
     *
     * @return A TargetLocator which can be used to select a frame, window, alert, context
     * @see TargetLocator
     */
    public TargetLocator switchTo() {
        return new TargetLocator(seleniumWebDriver().switchTo());
    }

    /**
     * Used to locate a given frame, window, alert, context.
     */
    public class TargetLocator {
        protected final org.openqa.selenium.WebDriver.TargetLocator targetLocator;

        protected TargetLocator(org.openqa.selenium.WebDriver.TargetLocator targetLocator) {
            this.targetLocator = targetLocator;
        }

        /**
         * Select a frame by its (zero-based) index. Selecting a frame by index is equivalent to the
         * JS expression window.frames[index] where "window" is the DOM window represented by the
         * current context. Once the frame has been selected, all subsequent calls on the WebDriver
         * interface are made to that frame.
         *
         * @param index (zero-based) index
         * @return This driver focused on the given frame
         * @throws NoSuchFrameException If the frame cannot be found
         */
        public WebDriver frame(int index) {
            targetLocator.frame(index);
            return WebDriver.this;
        }

        /**
         * Select a frame by its name or ID. Frames located by matching name attributes are always given
         * precedence over those matched by ID.
         *
         * @param nameOrId the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt;
         *                 element, or the (zero-based) index
         * @return This driver focused on the given frame
         * @throws NoSuchFrameException If the frame cannot be found
         */
        public WebDriver frame(String nameOrId) {
            targetLocator.frame(nameOrId);
            return WebDriver.this;
        }

        /**
         * Select a frame using {@link Element}.
         *
         * @param frameElement The frame element to switch to.
         * @return This driver focused on the given frame.
         * @throws NoSuchFrameException If the given element is neither an IFRAME nor a FRAME element.
         * @throws ElementTimeoutException If the Element doesn't exist within timeout
         */
        public WebDriver frame(Element frameElement) {
            frameElement.waitFor().exists();
            targetLocator.frame(frameElement.seleniumElement());
            return WebDriver.this;
        }

        /**
         * Change focus to the parent context. If the current context is the top level browsing context,
         * the context remains unchanged.
         *
         * @return This driver focused on the parent frame
         */
        public WebDriver parentFrame() {
            targetLocator.parentFrame();
            return WebDriver.this;
        }

        /**
         * Switch the focus of future commands for this driver to the new opened window.
         * <p>
         * Example:<pre>{@code
         *   Set<String> previousWindowHandles = webDriver.getWindowHandles();
         *   // do something to open a new window
         *   webDriver.switchTo().newWindow(previousWindowHandles);
         * }</pre>
         * </p>
         *
         * @param previousWindowHandles The previous window handles as returned by {@link #getWindowHandles()}
         * @return This driver focused on the new window
         * @throws WebDriverTimeoutException If no new window opened within timeout
         */
        public WebDriver newWindow(Set<String> previousWindowHandles) {

            long startTime = System.currentTimeMillis();

            Set<String> newWindowHandles = new LinkedHashSet<>();
            for (String windowHandle : getWindowHandles()) {
                if (!previousWindowHandles.contains(windowHandle)) {
                    newWindowHandles.add(windowHandle);
                }
            }
            if (!newWindowHandles.isEmpty()) {
                return window(((String[]) newWindowHandles.toArray())[0]);
            }

            while ((System.currentTimeMillis() - startTime) <= getWaitTimeout()) {
                try {
                    Thread.sleep(getWaitInterval());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                newWindowHandles = new LinkedHashSet<>();
                for (String windowHandle : getWindowHandles()) {
                    if (!previousWindowHandles.contains(windowHandle)) {
                        newWindowHandles.add(windowHandle);
                    }
                }
                if (!newWindowHandles.isEmpty()) {
                    return window(((String[]) newWindowHandles.toArray())[0]);
                }
            }

            throw new WebDriverTimeoutException("Timed out waiting for new window opened in:", WebDriver.this);
        }

        /**
         * Switch the focus of future commands for this driver to the window with the given name/handle.
         *
         * @param nameOrHandle The name of the window or the handle as returned by
         *                     {@link #getWindowHandles()}
         * @return This driver focused on the given window
         * @throws NoSuchWindowException If the window cannot be found
         */
        public WebDriver window(String nameOrHandle) {
            targetLocator.window(nameOrHandle);
            return WebDriver.this;
        }

        /**
         * Selects either the first frame on the page, or the main document when a page contains
         * iframes.
         *
         * @return This driver focused on the top window/first frame.
         */
        public WebDriver defaultContent() {
            targetLocator.defaultContent();
            return WebDriver.this;
        }

        /**
         * Switches to the currently active modal dialog for this particular driver instance.
         *
         * @return A handle to the dialog.
         * @throws NoAlertPresentException If the dialog cannot be found
         */
        public Alert alert() {
            waitFor().alertPresent();
            return targetLocator.alert();
        }

        /**
         * Switch the focus of future commands for this driver to the context with the given name.
         * <p><b>Supported by MOBILE</b></p>
         *
         * @param name The name of the context as returned by {@link #getContexts()}.
         * @return This driver focused on the given context.
         * @throws NoSuchContextException If the context cannot be found.
         */
        public WebDriver context(String name) {
            WebDriver.this.checkSupport(WebDriverType.MOBILE);

            ((ContextAware) seleniumWebDriver()).context(name);
            return WebDriver.this;
        }
    }

    /**
     * @return true if alert is present. False otherwise
     */
    public boolean isAlertPresent() {
        try {
            seleniumWebDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * An abstraction allowing the driver to access the browser's history and to navigate to a given
     * URL.
     *
     * @return A {@link Navigation} that allows the selection of what to
     * do next
     */
    public Navigation navigate() {
        return seleniumWebDriver().navigate();
    }

    /**
     * Executes JavaScript in the context of the currently selected frame or window. The script
     * fragment provided will be executed as the body of an anonymous function.
     * <p/>
     * <p/>
     * Within the script, use <code>document</code> to refer to the current document. Note that local
     * variables will not be available once the script has finished executing, though global variables
     * will persist.
     * <p/>
     * <p/>
     * If the script has a return value (i.e. if the script contains a <code>return</code> statement),
     * then the following steps will be taken:
     * <p/>
     * <ul>
     * <li>For an HTML element, this method returns a WebElement</li>
     * <li>For a decimal, a Double is returned</li>
     * <li>For a non-decimal number, a Long is returned</li>
     * <li>For a boolean, a Boolean is returned</li>
     * <li>For all other cases, a String is returned.</li>
     * <li>For an array, return a List&lt;Object&gt; with each object following the rules above. We
     * support nested lists.</li>
     * <li>Unless the value is null or there is no return value, in which null is returned</li>
     * </ul>
     * <p/>
     * <p/>
     * Arguments must be a number, a boolean, a String, WebElement, or a List of any combination of
     * the above. An exception will be thrown if the arguments do not meet these criteria. The
     * arguments will be made available to the JavaScript via the "arguments" magic variable, as if
     * the function were called via "Function.apply"
     *
     * @param script The JavaScript to execute
     * @param args   The arguments to the script. May be empty
     * @return One of Boolean, Long, String, List or null.
     */
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
        return ((JavascriptExecutor) seleniumWebDriver()).executeScript(script, convertedArgs);
    }

    /**
     * Execute an asynchronous piece of JavaScript in the context of the currently selected frame or
     * window. Unlike executing {@link #executeScript(String, Object...) synchronous JavaScript},
     * scripts executed with this method must explicitly signal they are finished by invoking the
     * provided callback. This callback is always injected into the executed function as the last
     * argument.
     * <p/>
     * <p/>
     * The first argument passed to the callback function will be used as the script's result. This
     * value will be handled as follows:
     * <p/>
     * <ul>
     * <li>For an HTML element, this method returns a WebElement</li>
     * <li>For a number, a Long is returned</li>
     * <li>For a boolean, a Boolean is returned</li>
     * <li>For all other cases, a String is returned.</li>
     * <li>For an array, return a List&lt;Object&gt; with each object following the rules above. We
     * support nested lists.</li>
     * <li>Unless the value is null or there is no return value, in which null is returned</li>
     * </ul>
     * <p/>
     * <p/>
     * The default timeout for a script to be executed is 0ms. In most cases, including the examples
     * below, one must set the script timeout
     * {@link Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)}  beforehand
     * to a value sufficiently large enough.
     * <p/>
     * <p/>
     * <p/>
     * Example #1: Performing a sleep in the browser under test. <pre>{@code
     *   long start = System.currentTimeMillis();
     *   ((JavascriptExecutor) driver).executeAsyncScript(
     *       "window.setTimeout(arguments[arguments.length - 1], 500);");
     *   System.out.println(
     *       "Elapsed time: " + System.currentTimeMillis() - start);
     * }</pre>
     * <p/>
     * <p/>
     * Example #2: Synchronizing a test with an AJAX application: <pre>{@code
     *   WebElement composeButton = driver.findElement(By.id("compose-button"));
     *   composeButton.click();
     *   ((JavascriptExecutor) driver).executeAsyncScript(
     *       "var callback = arguments[arguments.length - 1];" +
     *       "mailClient.getComposeWindowWidget().onload(callback);");
     *   driver.switchTo().frame("composeWidget");
     *   driver.findElement(By.id("to")).sendKeys("bog@example.com");
     * }</pre>
     * <p/>
     * <p/>
     * Example #3: Injecting a XMLHttpRequest and waiting for the result: <pre>{@code
     *   Object response = ((JavascriptExecutor) driver).executeAsyncScript(
     *       "var callback = arguments[arguments.length - 1];" +
     *       "var xhr = new XMLHttpRequest();" +
     *       "xhr.open('GET', '/resource/data.json', true);" +
     *       "xhr.onreadystatechange = function() {" +
     *       "  if (xhr.readyState == 4) {" +
     *       "    callback(xhr.responseText);" +
     *       "  }" +
     *       "};" +
     *       "xhr.send();");
     *   JsonObject json = new JsonParser().parse((String) response);
     *   assertEquals("cheese", json.get("food").getAsString());
     * }</pre>
     * <p/>
     * <p/>
     * Script arguments must be a number, a boolean, a String, WebElement, or a List of any
     * combination of the above. An exception will be thrown if the arguments do not meet these
     * criteria. The arguments will be made available to the JavaScript via the "arguments"
     * variable.
     *
     * @param script The JavaScript to execute.
     * @param args   The arguments to the script. May be empty.
     * @return One of Boolean, Long, String, List, WebElement, or null.
     * @see Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit)
     */
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
        return ((JavascriptExecutor) seleniumWebDriver()).executeAsyncScript(script, convertedArgs);
    }

    /**
     * @return the {@link Keyboard} of current driver
     */
    public Keyboard getKeyboard() {
        return ((HasInputDevices) seleniumWebDriver()).getKeyboard();
    }

    /**
     * @return the {@link Mouse} of current driver
     */
    public Mouse getMouse() {
        return ((HasInputDevices) seleniumWebDriver()).getMouse();
    }

    /**
     * @return the {@link Capabilities} of the current driver
     */
    public Capabilities getCapabilities() {
        return ((HasCapabilities) seleniumWebDriver()).getCapabilities();
    }

    /**
     * Quits this driver, closing every associated window.
     */
    public void quit() {
        seleniumWebDriver().quit();
    }

    @Override
    public String toString() {
        return String.format("WebDriver <WebDriverType: %s><SessionId: %s>", getWebDriverType(), ((RemoteWebDriver) seleniumWebDriver()).getSessionId());
    }

    /**
     * Scrolls the device to direction.
     * It will try to scroll in the first element of type scroll view, table or collection view it finds.
     * If you want to scroll in element, please use {@link Element#scroll(com.iselsoft.easyium.ScrollDirection)}
     *
     * @param direction the direction to scroll
     */
    public void scroll(ScrollDirection direction) {
        checkSupport(WebDriverType.MOBILE);

        HashMap<String, String> scrollParams = new HashMap<>();
        scrollParams.put("direction", direction.getValue());
        executeScript("mobile: scroll", scrollParams);
    }

    /**
     * Scroll to {@link Element}
     *
     * @param element the element to be scrolled to
     */
    public void scrollTo(Element element) {
        element.scrollIntoView();
    }

    /**
     * Attempt to forcibly kill this Killable at the OS level. Call when all hope is lost.
     * <p><b>Supported by FIREFOX</b></p>
     */
    public void kill() {
        checkSupport(WebDriverType.FIREFOX);

        ((Killable) seleniumWebDriver()).kill();
    }

    /**
     * <p><b>Supported by MOBILE, CHROME, OPERA</b></p>
     *
     * @return the {@link Location} of current driver
     */
    public Location getLocation() {
        checkSupport(WebDriverType.MOBILE, WebDriverType.CHROME, WebDriverType.OPERA);

        return ((LocationContext) seleniumWebDriver()).location();
    }

    /**
     * Set location for current driver.
     * <p><b>Supported by MOBILE, CHROME, OPERA</b></p>
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        checkSupport(WebDriverType.MOBILE, WebDriverType.CHROME, WebDriverType.OPERA);

        ((LocationContext) seleniumWebDriver()).setLocation(location);
    }

    /**
     * <p><b>Supported by CHROME, OPERA</b></p>
     *
     * @return the {@link LocalStorage} of current driver
     */
    public LocalStorage getLocalStorage() {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        return ((WebStorage) seleniumWebDriver()).getLocalStorage();
    }

    /**
     * <p><b>Supported by CHROME, OPERA</b></p>
     *
     * @return the {@link SessionStorage} of current driver
     */
    public SessionStorage getSessionStorage() {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        return ((WebStorage) seleniumWebDriver()).getSessionStorage();
    }

    /**
     * Return a set of context handles which can be used to iterate over all contexts of this
     * WebDriver instance
     * <p><b>Supported by MOBILE</b></p>
     *
     * @return A set of context handles which can be used to iterate over available contexts.
     */
    public Set<String> getContexts() {
        checkSupport(WebDriverType.MOBILE);

        return ((ContextAware) seleniumWebDriver()).getContextHandles();
    }

    /**
     * Return an opaque handle to this context that uniquely identifies it within this driver
     * instance. This can be used to switch to this context at a later date
     * <p><b>Supported by MOBILE</b></p>
     *
     * @return The current context handle
     */
    public String getCurrentContext() {
        checkSupport(WebDriverType.MOBILE);

        return ((ContextAware) seleniumWebDriver()).getContext();
    }

    /**
     * Changes the orientation of the web driver window.
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param orientation the desired screen orientation
     */
    public void rotate(ScreenOrientation orientation) {
        checkSupport(WebDriverType.MOBILE);

        ((Rotatable) seleniumWebDriver()).rotate(orientation);
    }

    /**
     * Alias for {@link #rotate(ScreenOrientation)}
     * <p><b>Supported by MOBILE</b></p>
     */
    public void setOrientation(ScreenOrientation orientation) {
        rotate(orientation);
    }

    /**
     * <p><b>Supported by MOBILE</b></p>
     *
     * @return the current {@link ScreenOrientation} of the web driver
     */
    public ScreenOrientation getOrientation() {
        checkSupport(WebDriverType.MOBILE);

        return ((Rotatable) seleniumWebDriver()).getOrientation();
    }

    /**
     * Hides the keyboard if it is showing.
     * On iOS, there are multiple strategies for hiding the keyboard. Defaults to the "tapOutside" strategy (taps outside the keyboard).
     * Switch to using hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done") if this doesn't work.
     * <p><b>Supported by MOBILE</b></p>
     */
    public void hideKeyboard() {
        checkSupport(WebDriverType.MOBILE);

        ((DeviceActionShortcuts) seleniumWebDriver()).hideKeyboard();
    }

    /**
     * Gets device date and time for both iOS(Supports only real device) and Android devices
     * <p><b>Supported by MOBILE</b></p>
     */
    public String getDeviceTime() {
        checkSupport(WebDriverType.MOBILE);

        return ((DeviceActionShortcuts) seleniumWebDriver()).getDeviceTime();
    }

    /**
     * Convenience method for swiping across the screen
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param startx   starting x coordinate
     * @param starty   starting y coordinate
     * @param endx     ending x coordinate
     * @param endy     ending y coordinate
     * @param duration amount of time in milliseconds for the entire swipe action to take
     */
    public void swipe(int startx, int starty, int endx, int endy, int duration) {
        checkSupport(WebDriverType.MOBILE);

        ((TouchShortcuts) seleniumWebDriver()).swipe(startx, starty, endx, endy, duration);
    }

    /**
     * Convenience method for flicking across the screen
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param startx starting x coordinate
     * @param starty starting y coordinate
     * @param endx   ending x coordinate
     * @param endy   ending y coordinate
     */
    public void flick(int startx, int starty, int endx, int endy) {
        checkSupport(WebDriverType.MOBILE);

        createTouchAction().press(startx, starty).moveTo(endx, endy).release().perform();
    }

    /**
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param remotePath On Android and iOS, this is either the path to the file
     *                   (relative to the root of the app's file system). On iOS only,
     *                   if path starts with /AppName.app, which will be replaced with
     *                   the application's .app directory
     * @return A byte array of Base64 encoded data.
     */
    public byte[] pullFile(String remotePath) {
        checkSupport(WebDriverType.MOBILE);

        return ((InteractsWithFiles) seleniumWebDriver()).pullFile(remotePath);
    }

    /**
     * Pull a folder from the simulator/device. Does not work on iOS Real
     * Devices, but works on simulators
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param remotePath On Android and iOS, this is either the path to the file
     *                   (relative to the root of the app's file system). On iOS only,
     *                   if path starts with /AppName.app, which will be replaced with
     *                   the application's .app directory
     * @return A byte array of Base64 encoded data, representing a ZIP ARCHIVE
     * of the contents of the requested folder.
     */
    public byte[] pullFolder(String remotePath) {
        checkSupport(WebDriverType.MOBILE);

        return ((InteractsWithFiles) seleniumWebDriver()).pullFolder(remotePath);
    }

    /**
     * Launch the app which was provided in the capabilities at session creation
     * <p><b>Supported by MOBILE</b></p>
     */
    public void launchApp() {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).launchApp();
    }

    /**
     * Install an app on the mobile device
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param appPath path to app to install
     */
    public void installApp(String appPath) {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).installApp(appPath);
    }

    /**
     * Checks if an app is installed on the device
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param bundleId bundleId of the app
     * @return True if app is installed, false otherwise
     */
    public boolean isAppInstalled(String bundleId) {
        checkSupport(WebDriverType.MOBILE);

        return ((InteractsWithApps) seleniumWebDriver()).isAppInstalled(bundleId);
    }

    /**
     * Reset the currently running app for this session
     * <p><b>Supported by MOBILE</b></p>
     */
    public void resetApp() {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).resetApp();
    }

    /**
     * Runs the current app as a background app for the number of seconds
     * requested. This is a synchronous method, it returns after the back has
     * been returned to the foreground.
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param milliseconds Number of milliseconds to run App in background
     */
    public void runAppInBackground(int milliseconds) {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).runAppInBackground(milliseconds / 1000);
    }

    /**
     * Remove the specified app from the device (uninstall)
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param bundleId the bunble identifier (or app id) of the app to remove
     */
    public void removeApp(String bundleId) {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).removeApp(bundleId);
    }

    /**
     * Close the app which was provided in the capabilities at session creation
     * <p><b>Supported by MOBILE</b></p>
     */
    public void closeApp() {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).closeApp();
    }

    /**
     * Get all defined Strings from an app for the default language
     * <p><b>Supported by MOBILE</b></p>
     *
     * @return a map with localized strings defined in the app
     */
    public Map<String, String> getAppStringMap() {
        checkSupport(WebDriverType.MOBILE);

        return ((HasAppStrings) seleniumWebDriver()).getAppStringMap();
    }

    /**
     * Get all defined Strings from an app for the specified language
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param language strings language code
     * @return a map with localized strings defined in the app
     */
    public Map<String, String> getAppStringMap(String language) {
        checkSupport(WebDriverType.MOBILE);

        return ((HasAppStrings) seleniumWebDriver()).getAppStringMap(language);
    }

    /**
     * Get all defined Strings from an app for the specified language and
     * strings filename
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param language   strings language code
     * @param stringFile strings filename
     * @return a map with localized strings defined in the app
     */
    public Map<String, String> getAppStringMap(String language, String stringFile) {
        checkSupport(WebDriverType.MOBILE);

        return ((HasAppStrings) seleniumWebDriver()).getAppStringMap(language, stringFile);
    }

    /**
     * Get settings stored for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to read
     * <p><b>Supported by MOBILE</b></p>
     *
     * @return JsonObject, a straight-up hash of settings
     */
    public JsonObject getSettings() {
        checkSupport(WebDriverType.MOBILE);

        return ((AppiumDriver) seleniumWebDriver()).getSettings();
    }

    /**
     * <p><b>Supported by MOBILE</b></p>
     *
     * @return the {@link URL} of remote server
     */
    public URL getRemoteAddress() {
        checkSupport(WebDriverType.MOBILE);

        return ((AppiumDriver) seleniumWebDriver()).getRemoteAddress();
    }

    /**
     * Send a key event to the device
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param key code for the key pressed on the device
     * @see AndroidKeyCode
     */
    public void pressKeyCode(int key) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).pressKeyCode(key);
    }

    /**
     * Send a key event along with an Android metastate to an Android device
     * Metastates are things like *shift* to get uppercase characters
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param key       code for the key pressed on the Android device
     * @param metastate metastate for the keypress
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     */
    public void pressKeyCode(int key, Integer metastate) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).pressKeyCode(key, metastate);
    }

    /**
     * Send a long key event to the device
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param key code for the key pressed on the device
     * @see AndroidKeyCode
     */
    public void longPressKeyCode(int key) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).longPressKeyCode(key);
    }

    /**
     * Send a long key event along with an Android metastate to an Android device
     * Metastates are things like *shift* to get uppercase characters
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param key       code for the key pressed on the Android device
     * @param metastate metastate for the keypress
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     */
    public void longPressKeyCode(int key, Integer metastate) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).longPressKeyCode(key, metastate);
    }

    /**
     * Get the current network settings of the device.
     * <p><b>Supported by ANDROID</b></p>
     *
     * @return {@link NetworkConnectionSetting} objects will let you inspect the status
     * of AirplaneMode, Wifi, Data connections
     */
    public NetworkConnectionSetting getNetworkConnection() {
        checkSupport(WebDriverType.ANDROID);

        return ((HasNetworkConnection) seleniumWebDriver()).getNetworkConnection();
    }

    /**
     * Set the network connection of the device. This is an Android-only method
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param connection The NetworkConnectionSetting configuration to use for the
     *                   device
     */
    public void setNetworkConnection(NetworkConnectionSetting connection) {
        checkSupport(WebDriverType.ANDROID);

        ((HasNetworkConnection) seleniumWebDriver()).setNetworkConnection(connection);
    }

    /**
     * Save base64 encoded data as a file on the remote mobile device.
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param remotePath Path to file to write data to on remote device
     * @param base64Data Base64 encoded byte array of data to write to remote device
     */
    public void pushFile(String remotePath, byte[] base64Data) {
        checkSupport(WebDriverType.ANDROID);

        ((PushesFiles) seleniumWebDriver()).pushFile(remotePath, base64Data);
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     * @param stopApp         If true, target app will be stopped. [Optional]
     */
    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity,
                              boolean stopApp) throws IllegalArgumentException {
        checkSupport(WebDriverType.ANDROID);

        ((StartsActivity) seleniumWebDriver()).startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity, stopApp);
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     */
    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity)
            throws IllegalArgumentException {
        checkSupport(WebDriverType.ANDROID);

        ((StartsActivity) seleniumWebDriver()).startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity);
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param appPackage  The package containing the activity. [Required]
     * @param appActivity The activity to start. [Required]
     */
    public void startActivity(String appPackage, String appActivity) throws IllegalArgumentException {
        checkSupport(WebDriverType.ANDROID);

        ((StartsActivity) seleniumWebDriver()).startActivity(appPackage, appActivity);
    }

    /**
     * Get test-coverage data
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param intent intent to broadcast
     * @param path   path to .ec file
     */
    public void endTestCoverage(String intent, String path) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).endTestCoverage(intent, path);
    }

    /**
     * Get the current activity being run on the mobile device
     * <p><b>Supported by ANDROID</b></p>
     *
     * @return a current activity being run on the mobile device
     */
    public String getCurrentActivity() {
        checkSupport(WebDriverType.ANDROID);

        return ((AndroidDriver) seleniumWebDriver()).currentActivity();
    }

    /**
     * Open the notification shade, on Android devices.
     * <p><b>Supported by ANDROID</b></p>
     */
    public void openNotifications() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).openNotifications();
    }

    /**
     * This method locks a device.
     * <p><b>Supported by ANDROID</b></p>
     */
    public void lock() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).lockDevice();
    }

    /**
     * This method unlocks a device.
     * <p><b>Supported by ANDROID</b></p>
     */
    public void unlock() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).unlockDevice();
    }

    /**
     * Lock the device (bring it to the lock screen) for a given number of
     * seconds
     * <p><b>Supported by MOBILE</b></p>
     *
     * @param milliseconds number of milliseconds to lock the screen for
     */
    public void lock(int milliseconds) {
        checkSupport(WebDriverType.MOBILE);

        if (getWebDriverType() == WebDriverType.ANDROID) {
            lock();
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            unlock();
        } else {
            ((IOSDriver) seleniumWebDriver()).lockDevice(milliseconds / 1000);
        }
    }

    /**
     * Check if the device is locked.
     * <p><b>Supported by ANDROID</b></p>
     *
     * @return true if device is locked. False otherwise
     */
    public boolean isLocked() {
        checkSupport(WebDriverType.ANDROID);

        return ((AndroidDriver) seleniumWebDriver()).isLocked();
    }

    /**
     * Toggle the location services on the device. 
     * <p><b>Supported by ANDROID</b></p>
     */
    public void toggleLocationServices() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).toggleLocationServices();
    }

    /**
     * Set the `ignoreUnimportantViews` setting. *Android-only method*
     * <p/>
     * Sets whether Android devices should use `setCompressedLayoutHeirarchy()`
     * which ignores all views which are marked IMPORTANT_FOR_ACCESSIBILITY_NO
     * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
     * by the system), in an attempt to make things less confusing or faster.
     * <p><b>Supported by ANDROID</b></p>
     *
     * @param compress ignores unimportant views if true, doesn't ignore otherwise.
     */
    public void ignoreUnimportantViews(Boolean compress) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).ignoreUnimportantViews(compress);
    }

    /**
     * Hides the keyboard by pressing the button specified by keyName if it is
     * showing.
     * <p><b>Supported by IOS</b></p>
     *
     * @param keyName The button pressed by the mobile driver to attempt hiding the
     *                keyboard
     */
    public void hideKeyboard(String keyName) {
        checkSupport(WebDriverType.IOS);

        ((IOSDeviceActionShortcuts) seleniumWebDriver()).hideKeyboard(keyName);
    }

    /**
     * Hides the keyboard if it is showing. Available strategies are PRESS_KEY
     * and TAP_OUTSIDE. One taps outside the keyboard, the other presses a key
     * of your choosing (probably the 'Done' key). Hiding the keyboard often
     * depends on the way an app is implemented, no single strategy always
     * works.
     * <p><b>Supported by IOS</b></p>
     *
     * @param strategy HideKeyboardStrategy
     * @param keyName  a String, representing the text displayed on the button of the
     *                 keyboard you want to press. For example: "Done"
     */
    public void hideKeyboard(String strategy, String keyName) {
        checkSupport(WebDriverType.IOS);

        ((IOSDeviceActionShortcuts) seleniumWebDriver()).hideKeyboard(strategy, keyName);
    }

    /**
     * Simulate shaking the device
     * <p><b>Supported by IOS</b></p>
     */
    public void shake() {
        checkSupport(WebDriverType.IOS);

        ((IOSDeviceActionShortcuts) seleniumWebDriver()).shake();
    }
}

