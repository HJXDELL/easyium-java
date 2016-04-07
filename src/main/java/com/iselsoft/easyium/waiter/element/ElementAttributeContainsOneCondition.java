package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;

public class ElementAttributeContainsOneCondition extends ElementCondition {
    private final String attribute;
    private final String[] values;

    protected ElementAttributeContainsOneCondition(Element element, String attribute, String... values) {
        super(element);
        this.attribute = attribute;
        this.values = values;
    }

    @Override
    public boolean occurred() {
        String attributeValue = element.seleniumElement().getAttribute(attribute);
        for (String value : values) {
            if (attributeValue.contains(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String valuesStr = "";
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                valuesStr = "'" + values[i] + "'";
            } else {
                valuesStr += ", '" + values[i] + "'";
            }
        }
        return String.format("ElementAttributeContainsOne [element: \n%s\n][attribute: %s][values: %s]", element, attribute, valuesStr);
    }
}
