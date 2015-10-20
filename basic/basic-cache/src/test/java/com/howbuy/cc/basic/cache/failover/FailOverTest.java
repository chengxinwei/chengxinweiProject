package com.howbuy.cc.basic.cache.failover;

import com.howbuy.cc.basic.cache.common.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/9/29.
 */
public class FailOverTest extends BaseTest {

    @Autowired
    private FailOverService failOverService;

    @Test
    public void testMaxThread() throws InterruptedException {
        System.out.println(failOverService.time200ms());
        for (int i = 0; i < 20; i++) {
            final int index = i;
            Thread.sleep(10);
            new Thread() {
                public void run() {
                    System.out.println(index + " : " + failOverService.time200ms());
                }
            }.start();
        }
        Thread.sleep(10000);
    }


    @Test
    public void testTimeout() throws InterruptedException {
        System.out.println(failOverService.time1s());
        Thread.sleep(1000);
        for(int i = 0 ; i < 20 ; i ++){
            System.out.println(failOverService.time1s());
        }
    }


    @Test
    public void testException() throws InterruptedException {
        try {
            failOverService.exception();
        }catch (Exception e){
            //ignore
        }

        for (int i = 0; i < 20; i++) {
            final int index = i;
            Thread.sleep(10);
            new Thread() {
                public void run() {
                    System.out.println(index + " : " + failOverService.exception());
                }
            }.start();
        }
        Thread.sleep(1000);
    }

}
