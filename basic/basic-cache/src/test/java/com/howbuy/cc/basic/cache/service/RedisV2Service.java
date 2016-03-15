package com.howbuy.cc.basic.cache.service;

import com.howbuy.cc.basic.cache.annotation.Cache;
import com.howbuy.cc.basic.cache.annotation.CacheClear;
import com.howbuy.cc.basic.cache.annotation.CacheUpdate;
import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.cache.model.User;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
@Service
public class RedisV2Service {

    @Cache(cacheName = "redis.test" , cacheExpr = "#index" , timeout = 10)
    public User getUser(Integer index){
        User user = new User();
        user.setId(index);
        return user;
    }

    @Cache(cacheName = "redis.null" , cacheExpr = "1" , timeout = 20)
    public User getNull(boolean isNull){
        if(isNull){
            return null;
        }
        return new User();
    }

    @Cache(cacheName = "redis.test" , cacheExpr = "#index" , timeout = 1)
    public User getUser1Sec(Integer index){
        User user = new User();
        user.setId(index);
        return user;
    }

    @CacheClear(cacheName = "redis.test" , cacheExpr = "#index" )
    public User clearAfterUser(Integer index){
        return (User) SpringBean.getBean(RedisClient.class).get("redis.test" , index);
    }

    @CacheClear(cacheName = "redis.test" , cacheExpr = "#index" , beforeExecute = true)
    public User clearBeforeUser(Integer index){
        return (User) SpringBean.getBean(RedisClient.class).get("redis.test" , index);
    }

    @CacheUpdate
    @Cache(cacheName = "redis.user.test" , cacheExpr = "#user.id")
    public User update(User user){
        //update database
        return user;
    }
}
