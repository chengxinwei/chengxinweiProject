package com.howbuy.cc.basic.test.mybatis.dao;

import com.howbuy.cc.basic.cache.annotation.Cache;
import com.howbuy.cc.basic.cache.annotation.CacheClear;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import com.howbuy.cc.basic.test.model.Aaaa;
import com.howbuy.cc.basic.test.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/20.
 */
@Service
@SuppressWarnings("unused")
public class UserCacheDao {

    @Cacheable(value = CacheConstant.REDIS_CACHE_1H , key = "'user' + #id")
    public User getUserCache(int id){
        return null;
    }

    @CacheEvict(value = CacheConstant.REDIS_CACHE_1H , key = "'user' + #id")
    public void clearCache(int id){}


    @Cacheable(value = CacheConstant.REDIS_CACHE_1H , key = "'user' + #user.userCode")
    public User set(User user) {
        return user;
    }

    @Cache(cacheName = "user.v2", cacheExpr = "#id" )
    public User getUserCacheV2(int id){
        return null;
    }

    @CacheClear(cacheName = "user.v2", cacheExpr = "#id" )
    public void clearCacheV2(int id){}


    @Cache(cacheName = "user.v2", cacheExpr = "#user.userCode" )
    public User setV2(User user) {
        return user;
    }

    @Cache(cacheName = "text.v2", cacheExpr = "#text" )
    public String setString(String text) {
        return text;
    }

    @Cache(cacheName = "text.v2", cacheExpr = "#text" )
    public String getString(String text) {
        return null;
    }

    @CacheClear(cacheName = "text.v2", cacheExpr = "#text" )
    public void clearString(String text){}
}
