package com.test.context;

import com.test.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* 실제로 빈을 만들고 의존성을 주업하는 클래스
* */
public class BeanContainer {

    private final Map<Class<?>, Object> beanMap = new HashMap<>();
    private final BeanLoader beanLoader;

    public BeanContainer() {
        this.beanLoader = new BeanLoader();
        this.createBean();
        this.injectBean();
    }

    public <T> T getBean(Class<?> clazz) {
        return (T) this.beanMap.get(clazz);
    }

    public void createBean() {
        List<Class<?>> classes = beanLoader.getClasses();
        classes.forEach(clazz -> {
            try {
                Object obj = clazz.getDeclaredConstructor().newInstance();
                beanMap.put(clazz, obj);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public void injectBean() {
        Set<Map.Entry<Class<?>, Object>> entries = beanMap.entrySet();
        for (Map.Entry<Class<?>, Object> entry : entries) {
            Class<?> clazz = entry.getKey();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getDeclaredAnnotation(Autowired.class) != null) {
                    setField(entry, field);
                }
            }
        }

    }

    private void setField(Map.Entry<Class<?>, Object> entry, Field field) {
        field.setAccessible(true);
        Object obj2 = beanMap.get(field.getType());
        System.out.println(obj2);
        try {
            field.set(entry.getValue(), obj2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
