package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;

public class ElementExistenceCondition extends ElementCondition {

    protected ElementExistenceCondition(Element element) {
        super(element);
    }

    @Override
    public boolean occurred() {
        return this.element.exists();
    }

    @Override
    public String toString() {
        return String.format("ElementExistence [\n%s\n]", element);
    }
}
