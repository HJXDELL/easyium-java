package com.iselsoft.easyium.support;

import com.iselsoft.easyium.Element;

/**
 * Your modeling class for control should inherit this class.
 */
public class Control extends Model {
    protected final Element element;

    protected Control(Element element) {
        super(element);
        this.element = element;
    }

    public Element element() {
        return element;
    }
}
