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
        for(int i = 0 ; i < 7 ; i ++){
            try {
                System.out.println(dbService.getDate(0).getId());
            }catch (Exception e){
                e.printStackTrace();
                Thread.sleep(5*1000);
            }

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
