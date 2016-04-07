package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.Element;

public class ElementTimeoutException extends TimeoutException {
    public ElementTimeoutException(String message) {
        super(message);
    }

    public ElementTimeoutException(String message, Element element) {
        super(message, element);
    }
}
