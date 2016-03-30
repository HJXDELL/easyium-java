package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.exceptions.EasyiumException;

public class ElementVisibleCondition extends ElementCondition {
    
    protected ElementVisibleCondition(Element element) {
        super(element);
    }

    @Override
    public boolean occurred() throws EasyiumException{
        return element.isDisplayed();
    }

    @Override
    public String toString() {
        return String.format("ElementVisible [\n%s\n]", element);
    }
}
