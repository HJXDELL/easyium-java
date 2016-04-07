package com.iselsoft.easyium;

public interface Identifier {
    Identifier id = new Identifier() {
        @Override
        public String identify(Element element) {
            return "id=" + element.getAttribute("id");
        }
    };

    Identifier className = new Identifier() {
        @Override
        public String identify(Element element) {
            return "class=" + element.getAttribute("class");
        }
    };

    Identifier name = new Identifier() {
        @Override
        public String identify(Element element) {
            return "name=" + element.getAttribute("name");
        }
    };

    Identifier text = new Identifier() {
        @Override
        public String identify(Element element) {
            return String.format("xpath=.//*[.='%s')]", element.getText());
        }
    };

    String identify(Element element);
}
