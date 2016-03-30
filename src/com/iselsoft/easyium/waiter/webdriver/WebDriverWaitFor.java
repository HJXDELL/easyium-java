package com.iselsoft.easyium.waiter.webdriver;

import com.iselsoft.easyium.WebDriver;
import com.iselsoft.easyium.exceptions.EasyiumException;
import com.iselsoft.easyium.exceptions.WebDriverTimeoutException;

public class WebDriverWaitFor {
    protected final WebDriver webDriver;
    protected final long interval;
    protected final long timeout;
    protected boolean desiredOccurrence;
    
    public WebDriverWaitFor(WebDriver webDriver, long interval, long timeout) {
        this.webDriver = webDriver;
        this.interval = interval;
        this.timeout = timeout;
        this.desiredOccurrence = true;
    }
    
    protected void waitFor(WebDriverCondition condition, long interval, long timeout) throws EasyiumException, InterruptedException {
        long startTime = System.currentTimeMillis();

        if (condition.occurred() == desiredOccurrence) {
            return;
        }

        while ((System.currentTimeMillis() - startTime) <= timeout) {
            Thread.sleep(interval);
            if (condition.occurred() == desiredOccurrence) {
                return;
            }
        }

        throw new WebDriverTimeoutException(String.format("Timed out waiting for <%s> to be <%s>.", condition, desiredOccurrence));
    }

    public WebDriverWaitFor not() {
        desiredOccurrence = !desiredOccurrence;
        return this;
    }
    
    public void alertPresent() throws EasyiumException, InterruptedException {
        waitFor(new WebDriverAlertPresentCondition(webDriver), interval, timeout);
    }
    
    public void textPresent(String text) throws EasyiumException, InterruptedException {
        waitFor(new WebDriverTextPresentCondition(webDriver, text), interval, timeout);
    }
    
    public void urlEquals(String url) throws EasyiumException, InterruptedException {
        waitFor(new WebDriverURLEqualsConditioin(webDriver, url), interval, timeout);
    }
    
    // todo: activity present
}
