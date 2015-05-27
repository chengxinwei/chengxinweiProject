package com.howbuy.client.dubbo.action;

import com.howbuy.service.DubboDemoService;
import com.howbuy.service.DubboGroupDemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/5/25.
 */
public class DubboGroupDemoConsumer {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationGroupConsumer.xml" });
        context.start();

        DubboGroupDemoService dubboDemoService = (DubboGroupDemoService) context.getBean("demoService");

        while(true) {
            try {
                System.out.println(dubboDemoService.getList());
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
