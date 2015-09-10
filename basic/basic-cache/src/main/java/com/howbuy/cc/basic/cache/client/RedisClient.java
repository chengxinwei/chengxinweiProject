package com.howbuy.cc.basic.cache.client;

import com.howbuy.cc.basic.cache.constant.CacheCloseConstant;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.tp.common.redis.core.ops.SerializeOps;


/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class RedisClient extends CacheClient{

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


}
