package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.exceptions.EasyiumException;

public class ElementAttributeContainsAllCondition extends ElementCondition {
    private final String attribute;
    private final String[] values;

    protected ElementAttributeContainsAllCondition(Element element, String attribute, String... values) {
        super(element);
        this.attribute = attribute;
        this.values = values;
    }

    @Override
    public boolean occurred() throws EasyiumException {
        String attributeValue = element.seleniumElement().getAttribute(attribute);
        for (String value : values) {
            if (!attributeValue.contains(value)) {
                return false;
            }
        }
        return true;
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
        return String.format("ElementAttributeContainsAll [element: \n%s\n][attribute: %s][values: %s]", element, attribute, valuesStr);
    }
}
