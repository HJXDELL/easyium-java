package com.iselsoft.easyium.waiter;

/**
 * The condition to be wait for.
 */
public interface Condition {
    /**
     * @return true if the condition occurred. False otherwise
     * @throws Exception if exception occurs
     */
    boolean occurred() throws Exception;

    String toString();
}
