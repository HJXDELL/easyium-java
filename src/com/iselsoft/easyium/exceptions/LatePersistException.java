package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.Context;

public class LatePersistException extends EasyiumException {
    public LatePersistException(String message) {
        super(message);
    }

    public LatePersistException(String message, Context context) {
        super(message, context);
    }
}
