package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.service.RedisDbService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class RadisCacheTest extends BaseTest{

    @Autowired
    private RedisDbService dbService;


    @Test
    public void cacheTest() throws InterruptedException {
        for(int i = 0 ; i < 10 ; i ++){
            System.out.println(dbService.getUser(i).getId());
        }
    }


    @Test
    public void cacheClear(){
        dbService.clearDate(0);
    }



}
