package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.service.RedisDbService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheNameSpaceTest{

    public static void main(String[] args) {

        String[] xml = new String[]{"classpath:cache-namespace.xml" , "classpath:basic/spring/base-spring.xml"};
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(xml);

        RedisDbService redisDbService = context1.getBean(RedisDbService.class);
        System.out.println(redisDbService.getUser(11));

}
}
