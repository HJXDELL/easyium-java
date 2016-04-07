package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;

public class ElementTextEqualsCondition extends ElementCondition {
    private final String text;

    protected ElementTextEqualsCondition(Element element, String text) {
        super(element);
        this.text = text;
    }

    @Override
    public boolean occurred() {
        return element.seleniumElement().getText().equals(text);
    }

    @Override
    public String toString() {
        return String.format("ElementTextEquals [element: \\n%s\\n][text: %s]", element, text);
    }
}
