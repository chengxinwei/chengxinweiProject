package com.howbuy.cc.basic.cache.cache.redis;

import com.howbuy.cc.basic.cache.cache.common.RedisCache;
import com.howbuy.cc.basic.cache.constant.CacheConstant;

/**
 * Redis 和spring-Cache集成
 * Created by xinwei.cheng on 2015/5/29.
 */
@SuppressWarnings("unused")
public class RedisCache1H extends RedisCache {

    final int timeout = 60 * 60;

    @Override
    public String getName() {
        return CacheConstant.REDIS_CACHE_1H;
    }

    @Override
    public Integer getTimeout() {
        return timeout;
    }
}
