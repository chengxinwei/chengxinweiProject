package com.howbuy.cc.basic.cache.service;

import com.howbuy.cc.basic.cache.annotation.Cache;
import com.howbuy.cc.basic.cache.annotation.CacheClear;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.cache.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
@Service
public class EhService {

    @Cacheable(value = CacheConstant.EH_CACHE_5S , key = "'User.getUser.' + #index")
    public User getUser(Integer index){
        User user = new User();
        user.setId(index);
        System.out.println("do in method");
        return user;
    }


    @Cacheable(value = CacheConstant.EH_CACHE_5S , key = "'User.getUser5S.' + #index")
    public User getUser5Sec(Integer index){
       return this.getUser(index);
    }


    @CacheEvict(value = CacheConstant.EH_CACHE_5S , key = "'User.getUser.' + #index")
    public void clearUser(Integer index){

    }

    @Cacheable(value = CacheConstant.EH_CACHE_5M , key = "'User.getNull.' + #index")
    public User getNull(boolean isNull){
        if(isNull){
            return null;
        }
        return new User();
    }

}
