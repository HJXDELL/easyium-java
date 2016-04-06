package com.iselsoft.easyium.support;

import com.iselsoft.easyium.Context;
import com.iselsoft.easyium.StaticElement;
import com.iselsoft.easyium.WebDriver;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Model {
    protected final Context context;
    public final WebDriver webDriver;

    protected Model(Context context) {
        this.context = context;
        this.webDriver = context.getWebDriver();
        initElements(this.getClass());
    }
    
    private void initElements(Class<?> thisClass) {
        for (Field field : thisClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String type = field.getType().toString();
                FoundBy annotation = field.getAnnotation(FoundBy.class);
                if (annotation != null && field.get(this) == null && !Modifier.isStatic(field.getModifiers()) &&
                        (type.equals("class com.iselsoft.easyium.Element") || type.equals("class com.iselsoft.easyium.StaticElement"))) {
                    field.set(this, new StaticElement(context, annotation.locator()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Class<?> superClass = thisClass.getSuperclass();
        if (!superClass.getPackage().getName().equals("com.iselsoft.easyium.support")) {
            initElements(superClass);
        } 
    }
}
