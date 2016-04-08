package com.iselsoft.easyium.support;

import com.iselsoft.easyium.Element;

/**
 * Your modeling class for control should inherit this class.
 */
public class Control extends Model {
    protected final Element element;

    protected Control(Element element) throws ReflectiveOperationException {
        super(element);
        this.element = element;
    }
}
