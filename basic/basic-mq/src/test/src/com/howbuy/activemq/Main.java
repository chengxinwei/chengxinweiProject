package com.howbuy.activemq;

import com.howbuy.activemq.sender.TestSender;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("classpath:application.xml");

        TestSender testSender = SpringBean.getBean(TestSender.class);

        try {
            testSender.sendMessage("111111111111");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(10000);
    }
}
