package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.service.RedisDbService;
import com.howbuy.cc.basic.config.Configuration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
