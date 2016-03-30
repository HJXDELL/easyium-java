package com.iselsoft.easyium.exceptions;

import com.iselsoft.easyium.Context;

public class EasyiumException extends RuntimeException {
    protected String message;
    protected Context context;

    public EasyiumException(String message) {
        super();
        this.message = message;
    }

    public EasyiumException(String message, Context context) {
        super();
        this.message = message;
        this.context = context;
    }

    public String toString() {
        String exceptionMessage = "";
        if (this.message != null) {
            exceptionMessage = this.message;
        }
        if (this.context != null) {
            exceptionMessage += "\n" + this.context;
        }
        return exceptionMessage;
    }
}
