package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.service.RedisDbService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheNameSpaceTest{

    public static void main(String[] args) throws InterruptedException {



        String[] xml = new String[]{"classpath:cache-namespace.xml"};
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(xml);

        RedisDbService redisDbService = context1.getBean(RedisDbService.class);

//        redisDbService.getUser(11).getId();

        while(true){
            int i = new Random().nextInt(100);
            redisDbService.getUser(i).getId();
        }
    }
}
