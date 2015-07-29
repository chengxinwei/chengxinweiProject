package com.howbuy.cc.basic.test;

import com.howbuy.cc.basic.test.dubbo.DubboConsumerTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/7/29.
 */
public class DubboMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("dubbo/application.xml");
        DubboConsumerTest.testSuccess();

    }

}
