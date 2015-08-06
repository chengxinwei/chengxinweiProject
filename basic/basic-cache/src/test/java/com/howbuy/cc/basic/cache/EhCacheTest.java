package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.service.EhDbService;
import com.howbuy.cc.basic.config.Configuration;
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

        for(int i = 0 ; i < 1 ; i ++){
            System.out.println(dbService.getDate(i).getId());
        }
    }


    @Test
    public void cacheClear(){
        dbService.clearDate(0);
    }


}
