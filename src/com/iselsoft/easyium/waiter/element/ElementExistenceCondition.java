package com.iselsoft.easyium.waiter.element;

import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.exceptions.EasyiumException;

public class ElementExistenceCondition extends ElementCondition {
    
    protected ElementExistenceCondition(Element element) {
        super(element);
    }

    @Override
    public boolean occurred() throws EasyiumException{
        return this.element.exists();
    }
    
    @Override
    public String toString() {
        return String.format("ElementExistence [\n%s\n]", element);
    }
}
