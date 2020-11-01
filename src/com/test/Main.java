package com.test;

import com.test.context.BeanContainer;
import com.test.context.BeanLoader;
import com.test.di.TargetBean;

public class Main {

    public static void main(String[] args) {

        BeanLoader beanLoader = new BeanLoader();
        beanLoader.loadClasses();
        BeanContainer beanContainer = new BeanContainer();

        TargetBean bean = beanContainer.getBean(TargetBean.class);

        bean.hello();

    }

}
