package com.iselsoft.easyium;

import org.openqa.selenium.internal.HasIdentity;

public class StaticElement extends Element {
    public StaticElement(Context parent, String locator) {
        super(parent);
        this.locator = locator;
    }

    @Override
    protected void refresh() {
        this.seleniumElement = null;
        this.seleniumElement = getParent().findSeleniumElement(locator);
    }

    /**
     * StaticElement already has a locator.
     */
    @Override
    public void persist() {
        getParent().persist();
    }

    @Override
    public String toString() {
        if (seleniumElement == null) {
            return String.format("%s\n|- StaticElement <SeleniumElement: null><Locator: %s>",
                    getParent(), locator);
        }
        return String.format("%s\n|- StaticElement <SeleniumElementId: %s><Locator: %s>",
                getParent(), ((HasIdentity) seleniumElement).getId(), locator);
    }
}
