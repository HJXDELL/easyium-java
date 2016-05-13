package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;
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

    protected void waitFor(ElementCondition condition, long interval, long timeout) {
        long startTime = System.currentTimeMillis();

        if (condition.occurred() == desiredOccurrence) {
            return;
        }

        while ((System.currentTimeMillis() - startTime) <= timeout) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            if (condition.occurred() == desiredOccurrence) {
                return;
            }
        }

        throw new ElementTimeoutException(String.format("Timed out waiting for <%s> to be <%s>.", condition, desiredOccurrence));
    }

    /**
     * Wait for not.
     *
     * @return this ElementWaitFor instance
     */
    public ElementWaitFor not() {
        desiredOccurrence = !desiredOccurrence;
        return this;
    }

    /**
     * Wait for this element exists.
     */
    public void exists() {
        this.waitFor(new ElementExistenceCondition(element), interval, timeout);
    }

    /**
     * Wait for this element visible.
     */
    public void visible() {
        this.waitFor(new ElementVisibleCondition(element), interval, timeout);
    }

    /**
     * Wait for this element's text equals the expected text.
     *
     * @param text the expected text
     */
    public void textEquals(String text) {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementTextEqualsCondition(element, text), interval, restTimeout);
    }

    /**
     * Wait for this element's attribute value equals the expected value.
     *
     * @param attribute the attribute of this element
     * @param value     the expected value
     */
    public void attributeEquals(String attribute, String value) {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementAttributeEqualsCondition(element, attribute, value), interval, restTimeout);
    }

    /**
     * Wait for this element's attribute value contains one of the value list.
     *
     * @param attribute the attribute of this element
     * @param values    the expected values
     */
    public void attributeContainsOne(String attribute, String... values) {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementAttributeContainsOneCondition(element, attribute, values), interval, restTimeout);
    }

    /**
     * Wait for this element's attribute value contains all of the value list.
     *
     * @param attribute the attribute of this element
     * @param values    the expected values
     */
    public void attributeContainsAll(String attribute, String... values) {
        long startTime = System.currentTimeMillis();
        element.waitFor(interval, timeout).exists();
        long restTimeout = startTime + timeout - System.currentTimeMillis();
        this.waitFor(new ElementAttributeContainsAllCondition(element, attribute, values), interval, restTimeout);
    }
}
