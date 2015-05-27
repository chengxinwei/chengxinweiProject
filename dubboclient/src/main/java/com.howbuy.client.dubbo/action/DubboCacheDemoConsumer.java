package com.howbuy.client.dubbo.action;

import com.howbuy.service.DubboDemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/5/25.
 *
 * 不推荐使用缓存 dubbo
 * 1.可以更加纯粹一些 只做调用 缓存有服务端自行实现
 * 2.缓存的更新时一个问题，调用者无法知道缓存什么时候需要更新
 */


public class DubboCacheDemoConsumer {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationCacheConsumer.xml" });
        context.start();

        DubboDemoService dubboDemoService = (DubboDemoService) context.getBean("demoService");

        while(true) {
            System.out.println(dubboDemoService.getDate());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
