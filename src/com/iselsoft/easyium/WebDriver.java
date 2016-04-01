package com.iselsoft.easyium;

import com.google.gson.JsonObject;
import com.iselsoft.easyium.waiter.webdriver.WebDriverWaitFor;
import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.*;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Killable;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Map;
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

    public void get(String url) {
        seleniumWebDriver().get(url);
    }

    public void open(String url) {
        get(url);
    }

    public String getCurrentUrl() {
        return seleniumWebDriver().getCurrentUrl();
    }

    public String getTitle() {
        return seleniumWebDriver().getTitle();
    }

    public String getPageSource() {
        return seleniumWebDriver().getPageSource();
    }

    public void closeCurrentWindow() {
        seleniumWebDriver().close();
    }

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

    public String getCurrentWindowHandle() {
        return seleniumWebDriver().getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return seleniumWebDriver().getWindowHandles();
    }

    public Options manage() {
        return seleniumWebDriver().manage();
    }

    public TargetLocator switchTo() {
        return new TargetLocator(seleniumWebDriver().switchTo());
    }

    public class TargetLocator {
        protected final org.openqa.selenium.WebDriver.TargetLocator targetLocator;

        protected TargetLocator(org.openqa.selenium.WebDriver.TargetLocator targetLocator) {
            this.targetLocator = targetLocator;
        }

        public WebDriver frame(int index) {
            targetLocator.frame(index);
            return WebDriver.this;
        }

        public WebDriver frame(String nameOrId) {
            targetLocator.frame(nameOrId);
            return WebDriver.this;
        }

        public WebDriver frame(Element frameElement) {
            frameElement.waitFor().exists();
            targetLocator.frame(frameElement.seleniumElement());
            return WebDriver.this;
        }

        public WebDriver parentFrame() {
            targetLocator.parentFrame();
            return WebDriver.this;
        }

        public WebDriver window(String nameOrHandle) {
            targetLocator.window(nameOrHandle);
            return WebDriver.this;
        }

        public WebDriver defaultContent() {
            targetLocator.defaultContent();
            return WebDriver.this;
        }

        public Alert alert() {
            return targetLocator.alert();
        }

        public WebDriver context(String name) {
            WebDriver.this.checkSupport(WebDriverType.MOBILE);

            ((ContextAware) seleniumWebDriver()).context(name);
            return WebDriver.this;
        }
    }

    public Navigation navigate() {
        return seleniumWebDriver().navigate();
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
        return ((JavascriptExecutor) seleniumWebDriver()).executeScript(script, convertedArgs);
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
        return ((JavascriptExecutor) seleniumWebDriver()).executeAsyncScript(script, convertedArgs);
    }

    public Keyboard getKeyboard() {
        return ((HasInputDevices) seleniumWebDriver()).getKeyboard();
    }

    public Mouse getMouse() {
        return ((HasInputDevices) seleniumWebDriver()).getMouse();
    }

    public Capabilities getCapabilities() {
        return ((HasCapabilities) seleniumWebDriver()).getCapabilities();
    }

    public void quit() {
        seleniumWebDriver().quit();
    }

    @Override
    public String toString() {
        return String.format("WebDriver <WebDriverType: %s><SessionId: %s>", getWebDriverType(), ((RemoteWebDriver) seleniumWebDriver()).getSessionId());
    }

    public void kill() {
        checkSupport(WebDriverType.FIREFOX);

        ((Killable) seleniumWebDriver()).kill();
    }

    public Location getLocation() {
        checkSupport(WebDriverType.MOBILE, WebDriverType.CHROME, WebDriverType.OPERA);

        return ((LocationContext) seleniumWebDriver()).location();
    }

    public void setLocation(Location location) {
        checkSupport(WebDriverType.MOBILE, WebDriverType.CHROME, WebDriverType.OPERA);

        ((LocationContext) seleniumWebDriver()).setLocation(location);
    }

    public LocalStorage getLocalStorage() {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        return ((WebStorage) seleniumWebDriver()).getLocalStorage();
    }

    public SessionStorage getSessionStorage() {
        checkSupport(WebDriverType.CHROME, WebDriverType.OPERA);

        return ((WebStorage) seleniumWebDriver()).getSessionStorage();
    }

    public Set<String> getContexts() {
        checkSupport(WebDriverType.MOBILE);

        return ((ContextAware) seleniumWebDriver()).getContextHandles();
    }

    public String getCurrentContext() {
        checkSupport(WebDriverType.MOBILE);

        return ((ContextAware) seleniumWebDriver()).getContext();
    }

    public void rotate(ScreenOrientation orientation) {
        checkSupport(WebDriverType.MOBILE);

        ((Rotatable) seleniumWebDriver()).rotate(orientation);
    }

    public ScreenOrientation getOrientation() {
        checkSupport(WebDriverType.MOBILE);

        return ((Rotatable) seleniumWebDriver()).getOrientation();
    }

    public void hideKeyboard() {
        checkSupport(WebDriverType.MOBILE);

        ((DeviceActionShortcuts) seleniumWebDriver()).hideKeyboard();
    }

    public String getDeviceTime() {
        checkSupport(WebDriverType.MOBILE);

        return ((DeviceActionShortcuts) seleniumWebDriver()).getDeviceTime();
    }

    public void swipe(int startx, int starty, int endx, int endy, int duration) {
        checkSupport(WebDriverType.MOBILE);

        ((TouchShortcuts) seleniumWebDriver()).swipe(startx, starty, endx, endy, duration);
    }

    public byte[] pullFile(String remotePath) {
        checkSupport(WebDriverType.MOBILE);

        return ((InteractsWithFiles) seleniumWebDriver()).pullFile(remotePath);
    }

    public byte[] pullFolder(String remotePath) {
        checkSupport(WebDriverType.MOBILE);

        return ((InteractsWithFiles) seleniumWebDriver()).pullFolder(remotePath);
    }

    public void launchApp() {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).launchApp();
    }

    public void installApp(String appPath) {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).installApp(appPath);
    }


    public boolean isAppInstalled(String bundleId) {
        checkSupport(WebDriverType.MOBILE);

        return ((InteractsWithApps) seleniumWebDriver()).isAppInstalled(bundleId);
    }


    public void resetApp() {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).resetApp();
    }


    public void runAppInBackground(int milliseconds) {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).runAppInBackground(milliseconds / 1000);
    }


    public void removeApp(String bundleId) {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).removeApp(bundleId);
    }


    public void closeApp() {
        checkSupport(WebDriverType.MOBILE);

        ((InteractsWithApps) seleniumWebDriver()).closeApp();
    }

    public Map<String, String> getAppStringMap() {
        checkSupport(WebDriverType.MOBILE);

        return ((HasAppStrings) seleniumWebDriver()).getAppStringMap();
    }

    public Map<String, String> getAppStringMap(String language) {
        checkSupport(WebDriverType.MOBILE);

        return ((HasAppStrings) seleniumWebDriver()).getAppStringMap(language);
    }

    public Map<String, String> getAppStringMap(String language, String stringFile) {
        checkSupport(WebDriverType.MOBILE);

        return ((HasAppStrings) seleniumWebDriver()).getAppStringMap(language, stringFile);
    }

    public JsonObject getSettings() {
        checkSupport(WebDriverType.MOBILE);

        return ((AppiumDriver) seleniumWebDriver()).getSettings();
    }

    public URL getRemoteAddress() {
        checkSupport(WebDriverType.MOBILE);

        return ((AppiumDriver) seleniumWebDriver()).getRemoteAddress();
    }

    public void pressKeyCode(int key) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).pressKeyCode(key);
    }

    public void pressKeyCode(int key, Integer metastate) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).pressKeyCode(key, metastate);
    }

    public void longPressKeyCode(int key) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).longPressKeyCode(key);
    }

    public void longPressKeyCode(int key, Integer metastate) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDeviceActionShortcuts) seleniumWebDriver()).longPressKeyCode(key, metastate);
    }

    public NetworkConnectionSetting getNetworkConnection() {
        checkSupport(WebDriverType.ANDROID);

        return ((HasNetworkConnection) seleniumWebDriver()).getNetworkConnection();
    }

    public void setNetworkConnection(NetworkConnectionSetting connection) {
        checkSupport(WebDriverType.ANDROID);

        ((HasNetworkConnection) seleniumWebDriver()).setNetworkConnection(connection);
    }

    public void pushFile(String remotePath, byte[] base64Data) {
        checkSupport(WebDriverType.ANDROID);

        ((PushesFiles) seleniumWebDriver()).pushFile(remotePath, base64Data);
    }

    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity,
                              boolean stopApp) throws IllegalArgumentException {
        checkSupport(WebDriverType.ANDROID);

        ((StartsActivity) seleniumWebDriver()).startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity, stopApp);
    }

    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity)
            throws IllegalArgumentException {
        checkSupport(WebDriverType.ANDROID);

        ((StartsActivity) seleniumWebDriver()).startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity);
    }

    public void startActivity(String appPackage, String appActivity) throws IllegalArgumentException {
        checkSupport(WebDriverType.ANDROID);

        ((StartsActivity) seleniumWebDriver()).startActivity(appPackage, appActivity);
    }

    public void endTestCoverage(String intent, String path) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).endTestCoverage(intent, path);
    }

    public String getCurrentActivity() {
        checkSupport(WebDriverType.ANDROID);

        return ((AndroidDriver) seleniumWebDriver()).currentActivity();
    }

    public void openNotifications() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).openNotifications();
    }

    public void lock() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).lockDevice();
    }

    public void unlock() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).unlockDevice();
    }

    public void lock(int milliseconds) {
        checkSupport(WebDriverType.MOBILE);

        if (getWebDriverType() == WebDriverType.ANDROID) {
            lock();
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            unlock();
        } else {
            ((IOSDriver) seleniumWebDriver()).lockDevice(milliseconds / 1000);
        }
    }

    public boolean isLocked() {
        checkSupport(WebDriverType.ANDROID);

        return ((AndroidDriver) seleniumWebDriver()).isLocked();
    }

    public void toggleLocationServices() {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).toggleLocationServices();
    }

    public void ignoreUnimportantViews(Boolean compress) {
        checkSupport(WebDriverType.ANDROID);

        ((AndroidDriver) seleniumWebDriver()).ignoreUnimportantViews(compress);
    }

    public void hideKeyboard(String keyName) {
        checkSupport(WebDriverType.IOS);

        ((IOSDeviceActionShortcuts) seleniumWebDriver()).hideKeyboard(keyName);
    }

    public void hideKeyboard(String strategy, String keyName) {
        checkSupport(WebDriverType.IOS);

        ((IOSDeviceActionShortcuts) seleniumWebDriver()).hideKeyboard(strategy, keyName);
    }

    public void shake() {
        checkSupport(WebDriverType.IOS);

        ((IOSDeviceActionShortcuts) seleniumWebDriver()).shake();
    }
}

