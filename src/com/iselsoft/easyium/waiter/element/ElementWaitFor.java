package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.exceptions.EasyiumException;
import com.iselsoft.easyium.exceptions.ElementTimeoutException;

public class ElementWaitFor {
    protected final Element element;
    protected final long interval;
    protected final long timeout;
    protected boolean desiredOccurrence;

    public ElementWaitFor(Element element, long interval, long timeout) {
        this.element = element;
        this.interval = interval;
        this.timeout = timeout;
        this.desiredOccurrence = true;
    }

    protected void waitFor(ElementCondition condition, long interval, long timeout) throws EasyiumException, InterruptedException {
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
        
        throw new ElementTimeoutException(String.format("Timed out waiting for <%s> to be <%s>.", condition, desiredOccurrence));
    }

    public ElementWaitFor not() {
        desiredOccurrence = !desiredOccurrence;
        return this;
    }
    
    public void exists() throws EasyiumException, InterruptedException {
        this.waitFor(new ElementExistenceCondition(element), interval, timeout);
    }
    
    public void visible() throws EasyiumException, InterruptedException {
        this.waitFor(new ElementVisibleCondition(element), interval, timeout);
    }
    
    public void textEquals(String text) throws EasyiumException, InterruptedException {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementTextEqualsCondition(element, text), interval, restTimeout);
    }
    
    public void attributeEquals(String attribute, String value) throws EasyiumException, InterruptedException {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementAttributeEqualsCondition(element, attribute, value), interval, restTimeout);
    }
    
    public void attributeContainsOne(String attribute, String... values) throws EasyiumException, InterruptedException {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementAttributeContainsOneCondition(element, attribute, values), interval, restTimeout);
    }

    public void attributeContainsAll(String attribute, String... values) throws EasyiumException, InterruptedException {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementAttributeContainsAllCondition(element, attribute, values), interval, restTimeout);
    }
}
