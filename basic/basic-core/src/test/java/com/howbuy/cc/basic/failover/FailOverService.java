package com.howbuy.cc.basic.failover;

import com.howbuy.cc.basic.failover.annation.FailOver;
import com.howbuy.cc.basic.failover.handler.EHCacheFailOverHandler;
import org.omg.SendingContext.RunTime;
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
        return "time200ms";
    }

    @FailOver(handlerClass =  EHCacheFailOverHandler.class)
    public String time1s(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
