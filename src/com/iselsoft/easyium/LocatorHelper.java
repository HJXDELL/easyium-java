package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.InvalidLocatorException;
import org.openqa.selenium.By;


public class LocatorHelper {
    public static By toBy(String locator) throws InvalidLocatorException {
        String[] separatedLocator = locator.split("=", 2);
        if (separatedLocator.length != 2) {
            throw new InvalidLocatorException("Separator '=' is not found.");
        }
        String by = separatedLocator[0];
        String value = separatedLocator[1];
        if (by.equals("id")) {
            return By.id(value);
        } else if (by.equals("xpath")) {
            return By.xpath(value);
        } else if (by.equals("link")) {
            return By.linkText(value);
        } else if (by.equals("partial_link")) {
            return By.partialLinkText(value);
        } else if (by.equals("name")) {
            return By.name(value);
        } else if (by.equals("tag")) {
            return By.tagName(value);
        } else if (by.equals("class")) {
            return By.className(value);
        } else if (by.equals("css")) {
            return By.cssSelector(value);
        } // todo: mobile by
        throw new InvalidLocatorException(String.format("The by <%s> of locator <%s> is not a valid By.", by, locator));
    }
}
