package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.Context;

public class NoSuchElementException extends EasyiumException {
    public NoSuchElementException(String message) {
        super(message);
    }

    public NoSuchElementException(String message, Context context) {
        super(message, context);
    }
}
