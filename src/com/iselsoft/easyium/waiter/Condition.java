package com.iselsoft.easyium.waiter;

public interface Condition {

    public boolean occurred() throws Exception;

    public String toString();
}
