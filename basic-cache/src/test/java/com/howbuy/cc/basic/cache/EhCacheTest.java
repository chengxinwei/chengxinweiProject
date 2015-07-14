package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.service.EhDbService;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.spring.SpringBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class EhCacheTest extends BaseTest{

    @Autowired
    private EhDbService dbService;



    @Test
    public void springTest(){
        System.out.println(Configuration.get("redis.sentinel.path"));
    }

    @Test
    public void cacheTest(){
        for(int i = 0 ; i < 8 ; i ++){
            System.out.println(dbService.getDate(0).getId());
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void cacheClear(){
        dbService.clearDate(0);
    }


}
