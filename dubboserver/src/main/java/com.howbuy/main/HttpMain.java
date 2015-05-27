package com.howbuy.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/5/25.
 */
public class HttpMain {


    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"applicationHttpProvider.xml"});
        context.start();

        System.out.println("按任意键退出");
        System.in.read();
    }

}
