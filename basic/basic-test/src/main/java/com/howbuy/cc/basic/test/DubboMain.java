package com.howbuy.cc.basic.test;

import com.alibaba.dubbo.container.Main;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/7/29.
 */
public class DubboMain {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("application.xml");
        Main.main(args);
    }

}
