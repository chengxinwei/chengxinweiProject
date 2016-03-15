package com.howbuy.cc.basic.cache.service;

import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.cache.model.User;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
@Service
public class RedisService {

    @Cacheable(value = CacheConstant.REDIS_CACHE_5S , key = "'redis.test.'+#index")
    public User getUser(Integer index){
        User user = new User();
        user.setId(index);
        return user;
    }

    @Cacheable(value = CacheConstant.REDIS_CACHE_5S , key = "'redis.isNull.'+#index")
    public User getNull(boolean isNull){
        if(isNull){
            return null;
        }
        return new User();
    }

    @Cacheable(value = CacheConstant.REDIS_CACHE_5S , key = "'redis.test.'+#index")
    public User getUser5Sec(Integer index){
        User user = new User();
        user.setId(index);
        return user;
    }

    @CacheEvict(value = CacheConstant.REDIS_CACHE_5S , key = "'redis.test.'+#index")
    public User clearAfterUser(Integer index){
        return (User) SpringBean.getBean(RedisClient.class).get("redis.test" , index);
    }
}
