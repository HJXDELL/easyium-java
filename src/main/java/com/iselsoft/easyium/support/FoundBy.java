package com.iselsoft.easyium.support;

import com.iselsoft.easyium.StaticElement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a field on a {@link Model} Object to indicate an alternative mechanism for locating the
 * element. This allows users to quickly and easily create {@link StaticElement}.
 * <p/>
 * You can use this annotation by specifying "locator" of the {@link StaticElement} to be created.
 * <p/>
 * For example, two ways to create a {@link StaticElement}:
 * <pre>
 * public class MyPage extends Page {
 *
 *     private Element myTextBox = new StaticElement(webDriver, "id=mytextbox");
 *
 *     {@literal @}FoundBy(locator = "id=mybutton")
 *     private Element myButton;
 *
 *     public MyPage(WebDriver webDriver) throws ReflectiveOperationException {
 *         super(webDriver);
 *     }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FoundBy {
    String locator() default "";
}
