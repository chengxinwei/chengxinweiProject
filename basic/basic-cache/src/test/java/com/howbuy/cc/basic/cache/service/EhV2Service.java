package com.howbuy.cc.basic.cache.service;

import com.howbuy.cc.basic.cache.annotation.Cache;
import com.howbuy.cc.basic.cache.annotation.CacheClear;
import com.howbuy.cc.basic.cache.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
@Service
public class EhV2Service {

    @Cache(cacheName = "ehcache.test" , cacheExpr = "#index" , cacheType = Cache.CacheType.ehcache )
    public User getUser(Integer index){
        User user = new User();
        user.setId(index);
        System.out.println("do in method");
        return user;
    }


    @Cache(cacheName = "ehcache.test.1sec" , cacheExpr = "#index" , cacheType = Cache.CacheType.ehcache , timeout = 1)
    public User getUser1Sec(Integer index){
       return this.getUser(index);
    }

    @CacheClear(cacheName = "ehcache.test" , cacheExpr = "#index" , cacheType = Cache.CacheType.ehcache)
    public void clearUser(Integer index){

    }


    @Cache(cacheName = "redis.null" , cacheExpr = "1" , timeout = 20 , cacheType = Cache.CacheType.ehcache)
    public User getNull(boolean isNull){
        if(isNull){
            return null;
        }
        return new User();
    }

}
