package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.Context;

public class NotPersistException extends EasyiumException {
    public NotPersistException(String message) {
        super(message);
    }

    public NotPersistException(String message, Context context) {
        super(message, context);
    }
}
