package com.iselsoft.easyium;

import java.util.List;

/**
 * FindElementsCondition is an interface to match the found element list, you can get the standard ones in this.
 * Otherwise, you can create one like this:
 * <pre>
 * context.findElements("class=foo", new FindElementCondition() {
 *      {@literal @}Override
 *      public boolean occurred(List{@literal <}Element{@literal >} elements) {
 *          return elements.size() {@literal >}= 2
 *      }
 * });
 * </pre>
 */
public interface FindElementsCondition {
    FindElementsCondition isNotEmpty = new FindElementsCondition() {
        @Override
        public boolean occurred(List<Element> elements) {
            return !elements.isEmpty();
        }

        @Override
        public String toString() {
            return "is not empty";
        }
    };

    FindElementsCondition isEmpty = new FindElementsCondition() {
        @Override
        public boolean occurred(List<Element> elements) {
            return elements.isEmpty();
        }

        @Override
        public String toString() {
            return "is empty";
        }
    };

    boolean occurred(List<Element> elements);
}
