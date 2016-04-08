package com.iselsoft.easyium;

/**
 * Identifier is a interface to generate the locator of the found element, you can get the standard ones in this.
 * Otherwise, you can create one like this:
 * <pre>
 * context.findElement("class=foo", new Identifier() {
 *      {@literal @}Override
 *      public String identify(Element element) {
 *          return String.format("xpath=.//*[@bar='%s']", element.getAttribute("bar"));
 *      }
 * });
 * <pre/>
 */
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
