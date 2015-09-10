package com.howbuy.cc.basic.cache.client;

import com.howbuy.cc.basic.cache.hit.HitCount;
import com.howbuy.cc.basic.cache.namespace.CacheHitOperationSource;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/8/31.
 */
public abstract class CacheClient {

    @Autowired(required = false)
    private CacheHitOperationSource cacheHitOperationSource;

    protected abstract void doPut(String key, Object value, int timeout);

    protected abstract Object doGet(String key);

    protected abstract void doClear(String key);

    public void clear(String cacheName, Object cacheVar){
        if(cacheName == null || cacheVar == null){
            return;
        }
        String key = CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString());
        doClear(key);
    }

    public void put(String cacheName, Object cacheVar, Object value, int timeout){
        if(cacheName == null || cacheVar == null || value == null){
            return;
        }
        String key = CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString());
        doPut(key , value , timeout);
    }


    public Object get(String cacheName, Object cacheVar){
        if(cacheName == null || cacheVar == null){
            return null;
        }
        String key = CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString());
        Object obj = doGet(key);
        if(obj == null){
            this.hitLog(cacheName, false);
            return null;
        }
        this.hitLog(cacheName, true);
        return obj;
    }


    protected void hitLog(String key , boolean hit){
        if(cacheHitOperationSource == null){
            return;
        }
        if(StringUtils.isEmpty(key)){
            return;
        }
        if(hit) {
            HitCount.incrHit(key);
        }else{
            HitCount.incrMiss(key);
        }
    }

}
