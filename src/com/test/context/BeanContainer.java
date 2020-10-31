package com.test.context;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanContainer {

    private Map<Class<?>, Object> beanMap = new HashMap<>();

    public BeanContainer() {
        createBean();
    }

    public <T> T getBean(Class<?> clazz) {
        return (T) this.beanMap.get(clazz);
    }

    public void createBean() {
        try {

            Class<?> targetClazz = Class.forName("com.test.di.TargetBean");
            Class<?> beanClazz = Class.forName("com.test.di.MyBean");
            Object target = targetClazz.getConstructor().newInstance();
            Object bean = beanClazz.getConstructor().newInstance();

            injectBean(target, bean);
            this.beanMap.put(targetClazz, target);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void injectBean(Object target, Object bean) {
        try {

            Field myBean = target.getClass().getDeclaredField("myBean");
            myBean.setAccessible(true);
            myBean.set(target, bean);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
