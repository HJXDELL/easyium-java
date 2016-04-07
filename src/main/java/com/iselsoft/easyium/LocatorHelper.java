package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.InvalidLocatorException;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

/**
 * This helper is used to convert locator to {@link org.openqa.selenium.By}. 
 * <p>
 * The format of locator is: "by=value", the possible values of "by" are:
 * <pre>
 * "id": By.id()
 * "xpath": By.xpath()
 * "link": By.linkText()
 * "partial_link": By.partialLinkText()
 * "name": By.name()
 * "tag": By.tagName()
 * "class": By.className()
 * "css": By.cssSelector()
 * "ios_uiautomation": MobileBy.IosUIAutomation()
 * "android_uiautomation": MobileBy.AndroidUIAutomator()
 * "accessibility_id": MobileBy.AccessibilityId()
 * </pre>
 */
public class LocatorHelper {
    protected static By toBy(String locator) {
        String[] separatedLocator = locator.split("=", 2);
        if (separatedLocator.length != 2) {
            throw new InvalidLocatorException("Separator '=' is not found.");
        }
        String by = separatedLocator[0];
        String value = separatedLocator[1];
        switch (by) {
            case "id":
                return By.id(value);
            case "xpath":
                return By.xpath(value);
            case "link":
                return By.linkText(value);
            case "partial_link":
                return By.partialLinkText(value);
            case "name":
                return By.name(value);
            case "tag":
                return By.tagName(value);
            case "class":
                return By.className(value);
            case "css":
                return By.cssSelector(value);
            case "ios_uiautomation":
                return MobileBy.IosUIAutomation(value);
            case "android_uiautomator":
                return MobileBy.AndroidUIAutomator(value);
            case "accessibility_id":
                return MobileBy.AccessibilityId(value);
        }
        throw new InvalidLocatorException(String.format("The by <%s> of locator <%s> is not a valid By.", by, locator));
    }
}
