package com.iselsoft.easyium;

/**
 * FindElementCondition is an interface to match the found element, you can get the standard ones in this interface.
 * Otherwise, you can create one like this:
 * <pre>
 * context.findElement("class=foo", new FindElementCondition() {
 *      {@literal @}Override
 *      public boolean occurred(Element element) {
 *          return !element.getText().isEmpty();
 *      }
 * });
 * </pre>
 */
public interface FindElementCondition {
    FindElementCondition isNotNull = new FindElementCondition() {
        @Override
        public boolean occurred(Element element) {
            return element != null;
        }

        @Override
        public String toString() {
            return "is not null";
        }
    };

    FindElementCondition isNull = new FindElementCondition() {
        @Override
        public boolean occurred(Element element) {
            return element == null;
        }

        @Override
        public String toString() {
            return "is null";
        }
    };

    boolean occurred(Element element);
}
