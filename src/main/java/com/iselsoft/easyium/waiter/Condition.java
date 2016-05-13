package com.iselsoft.easyium.waiter;

/**
 * The condition to be wait for.
 */
public interface Condition {
    /**
     * @return true if the condition occurred. False otherwise
     * @throws Exception
     */
    boolean occurred() throws Exception;

    String toString();
}
