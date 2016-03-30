package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.EasyiumException;
import com.iselsoft.easyium.exceptions.LatePersistException;
import com.iselsoft.easyium.exceptions.NotPersistException;
import org.openqa.selenium.WebElement;

public class DynamicElement extends Element {
    private final String foundBy;
    private final Identifier identifier;

    protected DynamicElement(Context parent, WebElement seleniumElement, String foundBy, Identifier identifier) {
        super(parent);
        this.seleniumElement = seleniumElement;
        this.foundBy = foundBy;
        this.identifier = identifier;
    }

    @Override
    protected void refreshMe() throws EasyiumException {
        if (locator == null) {
            throw new NotPersistException("persist() was not invoked so this Element cannot auto-refresh.", this);
        }
        seleniumElement = null;
        seleniumElement = getParent().findSeleniumElement(locator);
    }

    @Override
    public void persist() throws LatePersistException {
        getParent().persist();

        try {
            if (locator == null) {
                locator = identifier.identify(this);
            }
        } catch (NotPersistException e) {
            throw new LatePersistException("Trying to persist() a stale element. Try invoking persist() earlier.", this);
        }
    }

    @Override
    public String toString() {
        if (seleniumElement == null) {
            return String.format("%s\n|- DynamicElement <SeleniumElement: null><Locator: %s><FoundBy: %s>",
                    getParent(), locator, foundBy);
        }
        return String.format("%s\n|- DynamicElement <SeleniumElementId: %s><Locator: %s><FoundBy: %s>",
                getParent(), seleniumElement.hashCode(), locator, foundBy);
    }
}
