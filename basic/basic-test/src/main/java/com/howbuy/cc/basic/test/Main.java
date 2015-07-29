//package com.howbuy.cc.basic.test;
//
//import com.howbuy.cc.basic.test.service.TestService;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// * Created by xinwei.cheng on 2015/7/9.
// */
//public class Main {
//
//
//    public static void main(String[] args) {
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("application.xml");
//        TestService testService = classPathXmlApplicationContext.getBean(TestService.class);
//
//        for(int i = 0 ; i < 20 ; i ++) {
//            System.out.println(testService.getDate(0));
//            try {
//                Thread.sleep(1*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//}
