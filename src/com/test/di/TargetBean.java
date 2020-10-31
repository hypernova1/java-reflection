package com.test.di;

import com.test.annotation.Autowired;
import com.test.annotation.Component;

@Component
public class TargetBean {

    @Autowired
    private MyBean myBean;

    public void hello() {
        myBean.hello();
    }

}
