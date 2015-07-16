package com.howbuy.cc.basic.cache.cacheManager;

import org.springframework.cache.Cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * spring
 * Created by xinwei.cheng on 2015/5/29.
 */
@SuppressWarnings("unused")
public class CacheManager implements org.springframework.cache.CacheManager {

    private List<Cache> cacheList = new ArrayList<>();

    public Cache getCache(String name) {
        for(Cache cache : cacheList){
            if(cache.getName().equals(name)){
                return cache;
            }
        }
        return null;
    }



    public Collection<String> getCacheNames() {
        List<String> cacheNames = new ArrayList<>();
        for(Cache cache : cacheList){
            cacheNames.add(cache.getName());
        }
        return cacheNames;
    }



    public List<Cache> getCacheList() {
        return cacheList;
    }

    public void setCacheList(List<Cache> cacheList) {
        this.cacheList = cacheList;
    }
}