package com.test.di;

import com.test.annotation.Component;

@Component
public class MyBean {

    public void hello() {
        System.out.println("Hello World!");
    }

}
