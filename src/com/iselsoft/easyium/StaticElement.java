package com.iselsoft.easyium;

public class StaticElement extends Element {
    protected StaticElement(Context parent, String locator) {
        super(parent);
        this.locator = locator;
    }

    @Override
    protected void refresh() {
        this.seleniumElement = null;
        this.seleniumElement = getParent().findSeleniumElement(locator);
    }

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
                getParent(), seleniumElement.hashCode(), locator);
    }
}
