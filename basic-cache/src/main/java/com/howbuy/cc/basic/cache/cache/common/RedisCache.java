package com.howbuy.cc.basic.cache.cache.common;

import com.howbuy.cc.basic.cache.client.RedisCacheClient;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import org.apache.log4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Redis 和spring-Cache集成
 * Created by xinwei.cheng on 2015/5/29.
 */
@SuppressWarnings("unused")
public abstract class RedisCache implements Cache {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public abstract String getName();
    public abstract Integer getTimeout();

    @Override
    public Object getNativeCache() {
        return RedisCacheClient.getShardedJedis();
    }
    @Override
    public ValueWrapper get(Object key) {
        Object obj = RedisCacheClient.get(String.valueOf(key));
        if (obj == null) {
            return null;
        }
        return new SimpleValueWrapper(obj);
    }
    @Override
    public void put(Object key, Object value) {
        RedisCacheClient.set(String.valueOf(key) , value , getTimeout());
    }
    @Override
    public void evict(Object key) {
        RedisCacheClient.del(String.valueOf(key));
    }
    @Override
    public void clear() {
        throw new RuntimeException("not support");
    }

}
