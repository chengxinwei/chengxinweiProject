package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.cache.common.BaseTest;
import com.howbuy.cc.basic.cache.model.User;
import com.howbuy.cc.basic.cache.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class RedisCacheTest extends BaseTest {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisClient redisClient;

//    @Test
//    public void redisInc(){
//        redisClient.put("test" , "int" , new BigDecimal(1000000000012300L), 20000);
//        System.out.println(redisClient.incr("test", "int"));
//        System.out.println(redisClient.incr("test", "int", 10L));
//
//        System.out.println(redisClient.decr("test", "int"));
//        System.out.println(redisClient.decr("test", "int", 10L));
//    }

    @Test
    public void redisTest(){
        User user1 = redisService.getUser(1);
        User user2 = redisService.getUser(1);
        Assert.assertEquals(user1.getId(), user2.getId());
    }


    @Test
    public void redisTimeoutTest(){
        redisService.getUser5Sec(1);
        try {
            Thread.sleep(5100);
        } catch (InterruptedException e) {
        }
        Assert.assertNull(redisClient.get("redis.test", 1));
    }

    @Test
    public void redisClearAfterTest(){
        redisService.getUser(1);
        User user = redisService.clearAfterUser(1);
        Assert.assertNull(redisClient.get("redis.test", 1));
        Assert.assertNotNull(user);
    }


    @Test
    public void redisNullTest(){
        redisService.getNull(true);
        Assert.assertNotNull(redisService.getNull(false));
    }

    @Test
    public void testLog(){
        for(int i = 0 ; i < 10 ; i ++){
            redisService.getUser(i%5);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
