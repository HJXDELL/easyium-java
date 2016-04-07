package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;

public class ElementVisibleCondition extends ElementCondition {

    protected ElementVisibleCondition(Element element) {
        super(element);
    }

    @Override
    public boolean occurred() {
        return element.isDisplayed();
    }

    @Override
    public String toString() {
        return String.format("ElementVisible [\n%s\n]", element);
    }
}
