package com.howbuy.cc.basic.test.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/8/17.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        UserService userService = applicationContext.getBean(UserService.class);
        userService.test();
    }

}
