package com.howbuy.cc.basic.cache.failover;

import com.howbuy.cc.basic.failover.annation.FailOver;
import org.springframework.stereotype.Component;

/**
 * Created by xinwei.cheng on 2015/9/29.
 */
@Component
public class FailOverService {


    @FailOver(maxThreadCount = 10 , handlerClass = EHCacheFailOverHandler.class)
    public String time200ms(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in method");
        return "time200ms";
    }

    @FailOver(handlerClass =  EHCacheFailOverHandler.class)
    public String time1s(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("in method");
        return "time1s";
    }

    @FailOver(handlerClass =  EHCacheFailOverHandler.class)
    public String exception(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("test");
    }
}
