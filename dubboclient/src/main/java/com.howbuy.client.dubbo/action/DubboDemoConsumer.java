package com.howbuy.client.dubbo.action;

import com.howbuy.client.dubbo.filter.AvgListThread;
import com.howbuy.service.DubboDemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/5/25.
 */
public class DubboDemoConsumer {


    private static int threads = 10;

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationConsumer.xml" });
        context.start();

        final DubboDemoService dubboDemoService = (DubboDemoService) context.getBean("demoService");

        AvgListThread avgListThread = new AvgListThread();
        avgListThread.start();;

        for(int i = 0 ; i < threads ; i ++) {

            new Thread() {

                public void run() {
                    while (true)

                    {
                        try {
                            dubboDemoService.getDate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

}
