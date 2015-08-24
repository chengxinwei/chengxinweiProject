package com.howbuy.activemq;

import com.howbuy.activemq.transaction.TransactionTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:application.xml");

        TransactionTest transactionTest = classPathXmlApplicationContext.getBean(TransactionTest.class);

        while (true) {
            transactionTest.testCommit();
            Thread.sleep(1000);
        }

    }
}
