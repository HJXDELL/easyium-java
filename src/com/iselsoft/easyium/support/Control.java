package com.iselsoft.easyium.support;

import com.iselsoft.easyium.Element;

public class Control extends Model {
    public final Element element;

    public Control(Element element) throws ReflectiveOperationException {
        super(element);
        this.element = element;
    }
}
