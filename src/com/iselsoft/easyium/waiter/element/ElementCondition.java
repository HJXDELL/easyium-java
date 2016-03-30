package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.waiter.Condition;

public abstract class ElementCondition implements Condition {
    protected final Element element;

    public ElementCondition(Element element) {
        this.element = element;
    }

    @Override
    public abstract boolean occurred();
}
