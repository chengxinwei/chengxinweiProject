package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.cache.common.BaseTest;
import com.howbuy.cc.basic.cache.model.User;
import com.howbuy.cc.basic.cache.service.RedisV2Service;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class RedisCacheV2Test extends BaseTest {

    @Autowired
    private RedisV2Service redisService;
    @Autowired
    private RedisClient redisClient;

    @Test
    public void redisTest(){
        User user1 = redisService.getUser(1);
        User user2 = redisService.getUser(1);
        Assert.assertEquals(user1.getId(), user2.getId());
    }


    @Test
    public void redisTimeoutTest(){
        redisService.getUser1Sec(1);
        try {
            Thread.sleep(1010);
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
    public void redisClearBeforeTest(){
        redisService.getUser(1);
        User user = redisService.clearBeforeUser(1);
        Assert.assertNull(redisClient.get("redis.test", 1));
        Assert.assertNull(user);
    }


    @Test
    public void redisNullTest(){
        redisService.getNull(true);
        Assert.assertNotNull(redisService.getNull(false));
    }


    @Test
    public void redisApiClearTest(){
        redisClient.put("redis.test", 1, "abc", 1000);
        redisClient.clear("redis.test" , 1);
        System.out.println(redisClient.get("redis.test", 1));
        Assert.assertNull(redisClient.get("redis.test", 1));
    }

//    @Test
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

    @Test
    public void testRedisUpdate(){
        User user = new User();
        Integer random = new Random().nextInt(10);
        user.setId(1);
        user.setName("name" + random.toString());
        redisService.update(user);
        Assert.assertEquals(((User)(redisClient.get("redis.user.test", 1))).getName() , "name" + random.toString());
    }


    @Test
    public void incr(){
        redisClient.clear("test.incr" , "123");
        int i = 0;
        long y = redisClient.incr("test.incr.123");
        System.out.println(y);
        Assert.assertEquals(++i , y );
    }

    @Test
    public void decr(){
        redisClient.clear("test.decr" , "123");
        int i = 1;
        long y = redisClient.incr("test.decr.123");
        y = redisClient.decr("test.decr.123");
        System.out.println(y);
        Assert.assertEquals(--i , y );
    }


    @Test
    public void getOriginal(){
        redisClient.clear("test.getOriginal" , "123");
        long y = redisClient.incr("test.getOriginal.123");
        System.out.println(redisClient.getOriginal("test.getOriginal" , "123"));
    }
}
