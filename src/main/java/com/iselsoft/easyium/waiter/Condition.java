package com.iselsoft.easyium.waiter;

/**
 * The condition to be wait for.
 */
public interface Condition {
    /**
     * @return true if the condition occurred. False otherwise
     * @throws Exception
     */
    public boolean occurred() throws Exception;

    public String toString();
}
