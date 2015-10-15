package com.howbuy.cc.basic.cache.client;

import com.howbuy.cc.basic.cache.constant.CacheCloseConstant;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.tp.common.redis.core.ops.SerializeOps;


/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class RedisClient extends CacheClient{


    /**
     * 设置对象，设置的对象都会被序列化
     * 缺陷：如果是 number 也会被序列化 无法incr decr
     *      自增需要直接调用incr方法。
     * @param key
     * @param value
     * @param timeout
     */
    @Override
    protected void doPut(String key, Object value, int timeout){
        //关闭缓存开关，如果关闭缓存则不获取直接返回空
        Boolean close = Configuration.getBoolean(CacheCloseConstant.REDIS_CLOSE);
        if(close != null && close){
            return;
        }
        SerializeOps.set(key, value, timeout);
    }

    @Override
    protected Object doGet(String key){
        //关闭缓存开关，如果关闭缓存则不获取直接返回空
        Boolean close = Configuration.getBoolean(CacheCloseConstant.REDIS_CLOSE);
        if(close != null && close){
            return null;
        }
        return SerializeOps.get(key);
    }

    @Override
    protected void doClear(String key){
        SerializeOps.delete(key);
    }


//    public Long incr(String cacheName, Object cacheVar) {
//        return RedisOps.incr(CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString()));
//    }
//
//    public Long incr(String cacheName, Object cacheVar , Long value) {
//        return RedisOps.incrBy(CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString()), value);
//    }
//
//    public Long decr(String cacheName, Object cacheVar) {
//        return RedisOps.decr(CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString()));
//    }
//
//    public Long decr(String cacheName, Object cacheVar , Long value) {
//        return RedisOps.decrBy(CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString()), value);
//    }


}
