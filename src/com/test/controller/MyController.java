package com.test.controller;

import com.test.annotation.Autowired;
import com.test.service.MyService;

public class MyController {

    @Autowired
    private MyService myService;

    public void hello() {
        myService.hello();
    }

}
