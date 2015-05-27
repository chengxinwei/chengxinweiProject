package com.howbuy.client.dubbo.action;

import com.howbuy.service.DubboDemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;

/**
 * Created by xinwei.cheng on 2015/5/26.
 */
public class SpringRmiConsumer {


    public static void main(String[] args) throws RemoteException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationSpringConsumer.xml");
        DubboDemoService hs = (DubboDemoService) ctx.getBean("helloWorld");
        System.out.println(hs.getDate());
    }


}
