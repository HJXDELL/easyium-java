package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.exceptions.EasyiumException;

public class ElementAttributeEqualsCondition extends ElementCondition {
    private final String attribute;
    private final String value;

    protected ElementAttributeEqualsCondition(Element element, String attribute, String value) {
        super(element);
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public boolean occurred() throws EasyiumException {
        return element.seleniumElement().getAttribute(attribute).equals(value);
    }

    @Override
    public String toString() {
        return String.format("ElementAttributeEquals [element: \\n%s\\n][attribute: %s][value: %s]", element, attribute, value);
    }
}
