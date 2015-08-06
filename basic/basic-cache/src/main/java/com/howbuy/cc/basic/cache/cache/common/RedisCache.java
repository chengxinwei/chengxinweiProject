package com.howbuy.cc.basic.cache.cache.common;

import com.howbuy.cc.basic.cache.constant.CacheCloseConstant;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.tp.common.redis.core.ops.SerializeOps;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Redis 和spring-Cache集成
 * Created by xinwei.cheng on 2015/5/29.
 */
@SuppressWarnings("unused")
public abstract class RedisCache implements Cache {
    CCLogger logger = CCLogger.getLogger(this.getClass());

    public abstract Integer getTimeout();

    @Override
    public Object getNativeCache() {
        return SerializeOps.getClientList();
    }
    @Override
    public ValueWrapper get(Object key) {
        Object obj = null;
        try {
            //关闭缓存开关，如果关闭缓存则不获取直接返回空
            Boolean close = Configuration.getBoolean(CacheCloseConstant.REDIS_CLOSE);
            if(close != null && close){
                return null;
            }
            obj = SerializeOps.get(CacheKeyGenerator.getKeyStr(String.valueOf(key)));
        }catch(Exception e){
            logger.error( e.getMessage(), e);
        }
        if (obj == null) {
            return null;
        }
        return new SimpleValueWrapper(obj);
    }
    @Override
    public void put(Object key, Object value) {
        try {
            if(value == null){
                return;
            }
            SerializeOps.set(CacheKeyGenerator.getKeyStr(String.valueOf(key)), value, getTimeout());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void evict(Object key) {
        try {
            SerializeOps.delete(CacheKeyGenerator.getKeyStr(String.valueOf(key)));
        }catch (Exception e){
            logger.error( e.getMessage(), e);
        }

    }
    @Override
    public void clear() {
        throw new RuntimeException("not support");
    }

}
