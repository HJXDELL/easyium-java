package com.iselsoft.easyium;

import com.iselsoft.easyium.exceptions.NotPersistException;

public interface Identifier {
    public static Identifier id = new Identifier() {
        @Override
        public String identify(Element element) {
            return "id=" + element.getAttribute("id");
        }
    };
    
    public static Identifier className = new Identifier() {
        @Override
        public String identify(Element element) {
            return "class=" + element.getAttribute("class");
        }
    };
    
    public static Identifier name = new Identifier() {
        @Override
        public String identify(Element element) {
            return "name=" + element.getAttribute("name");
        }
    };
    
    public static Identifier linkText = new Identifier() {
        @Override
        public String identify(Element element) {
            return "link=" + element.getText();
        }
    };
    
    public String identify(Element element) throws NotPersistException;
}
