package com.howbuy.cc.basic.cache.service;

import com.howbuy.cc.basic.cache.model.User;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
@Service
public class EhDbService {

    int i = 0;


    @Cacheable(value = CacheConstant.EH_CACHE_5S, key = "#index")
    public User getDate(Integer index){
        User user = new User();
        user.setId(i++);
        return user;
    }


    @CacheEvict(value = CacheConstant.EH_CACHE_5S , key = "#index")
    public void clearDate(Integer index){

    }

}
