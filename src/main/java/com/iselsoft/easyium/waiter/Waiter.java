package com.iselsoft.easyium.waiter;

import com.iselsoft.easyium.exceptions.TimeoutException;

public class Waiter {
    public final static long DEFAULT_INTERVAL = 1000;
    public final static long DEFAULT_TIMEOUT = 30000;

    protected final long interval;
    protected final long timeout;

    public Waiter() {
        this(DEFAULT_INTERVAL, DEFAULT_TIMEOUT);
    }

    public Waiter(long timeout) {
        this(DEFAULT_INTERVAL, timeout);
    }

    public Waiter(long interval, long timeout) {
        this.interval = interval;
        this.timeout = timeout;
    }

    public void waitFor(Condition condition) throws Exception {
        long startTime = System.currentTimeMillis();

        if (condition.occurred()) {
            return;
        }

        while ((System.currentTimeMillis() - startTime) <= timeout) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            if (condition.occurred()) {
                return;
            }
        }

        throw new TimeoutException(String.format("Timed out waiting for <%s>.", condition.toString()));
    }
}
