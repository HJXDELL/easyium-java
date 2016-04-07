package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.Context;

public class InvalidLocatorException extends EasyiumException {
    public InvalidLocatorException(String message) {
        super(message);
    }

    public InvalidLocatorException(String message, Context context) {
        super(message, context);
    }
}
