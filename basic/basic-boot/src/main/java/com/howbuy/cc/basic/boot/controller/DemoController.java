package com.howbuy.cc.basic.boot.controller;

import com.howbuy.cc.basic.boot.bean.BeanTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/9/7.
 */
@RestController
public class DemoController {

    @Autowired
    private BeanTest beanTest;

    @RequestMapping("/")
    String home() {
        System.out.println(beanTest);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello World!";
    }

    @RequestMapping("/map")
    Map<String,Object> map() {
        Map<String,Object> params = new HashMap<>();
        params.put("a" , "b");
        return params;
    }

    @RequestMapping("/cn")
    Map<String,Object> cn() {
        Map<String,Object> params = new HashMap<>();
        params.put("a" , "中文");
        return params;
    }
}