package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.WebDriver;

public class WebDriverTimeoutException extends TimeoutException {
    public WebDriverTimeoutException(String message) {
        super(message);
    }

    public WebDriverTimeoutException(String message, WebDriver webDriver) {
        super(message, webDriver);
    }
}
