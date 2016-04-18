package com.iselsoft.easyium.support;

import com.iselsoft.easyium.Context;
import com.iselsoft.easyium.Element;
import com.iselsoft.easyium.StaticElement;
import com.iselsoft.easyium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Used to model all objects in your page.
 */
public abstract class Model {
    private final Context context;
    protected final WebDriver webDriver;

    protected Model(Context context) {
        this.context = context;
        this.webDriver = context.getWebDriver();
        initElements(this.getClass());
    }

    private void initElements(Class<?> thisClass) {
        try {
            for (Field field : thisClass.getDeclaredFields()) {
                FoundBy annotation = field.getAnnotation(FoundBy.class);
                field.setAccessible(true);
                if (annotation == null) {
                    continue;
                }
                if (field.get(this) != null) {
                    throw new AnnotationException("Cannot add @FoundBy for field with value.");
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    throw new AnnotationException("Cannot add @FoundBy for static field.");
                }
                Class<?> type = field.getType();
                if (Control.class.isAssignableFrom(type)) {
                    Constructor<?> constructor;
                    try {
                        constructor = type.getDeclaredConstructor(Element.class);
                    } catch (NoSuchMethodException e) {
                        throw new AnnotationException(String.format("No such constructor %s(com.iselsoft.easyium.Element element).", thisClass.getName()));
                    }
                    constructor.setAccessible(true);
                    field.set(this, constructor.newInstance(new StaticElement(context, annotation.locator())));
                    continue;
                }
                if (type.equals(Element.class) || type.equals(StaticElement.class)) {
                    field.set(this, new StaticElement(context, annotation.locator()));
                    continue;
                }
                throw new AnnotationException("Cannot add @FoundBy for field whose type is not " +
                        "Element or StaticElement or subclass of Control.");
            }
            Class<?> superclass = thisClass.getSuperclass();
            if (!superclass.equals(Control.class) && !superclass.equals(Page.class)) {
                initElements(superclass);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
