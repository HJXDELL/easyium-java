package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.Context;

public class TimeoutException extends EasyiumException {
    public TimeoutException(String message) {
        super(message);
    }
    
    public TimeoutException(String message, Context context) {
        super(message, context);
    }
}
