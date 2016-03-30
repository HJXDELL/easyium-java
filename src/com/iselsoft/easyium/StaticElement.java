package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.EasyiumException;
import com.iselsoft.easyium.exceptions.LatePersistException;

public class StaticElement extends Element {
    protected StaticElement(Context parent, String locator) {
        super(parent);
        this.locator = locator;
    }

    @Override
    protected void refreshMe() throws EasyiumException {
        this.seleniumElement = null;
        this.seleniumElement = getParent().findSeleniumElement(locator);
    }

    @Override
    public void persist() throws LatePersistException {
        getParent().persist();
    }
    
    @Override
    public String toString() {
        if (seleniumElement == null) {
            return String.format("%s\n|- StaticElement <SeleniumElement: null><Locator: %s>",
                    getParent(), locator);
        }
        return String.format("%s\n|- StaticElement <SeleniumElementId: %s><Locator: %s>",
                getParent(), seleniumElement.hashCode(), locator);
    }
}
