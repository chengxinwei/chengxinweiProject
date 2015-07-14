package com.howbuy.cc.basic.cache.cache.ehcache;

import com.howbuy.cc.basic.cache.cache.common.EhCache;
import com.howbuy.cc.basic.cache.cache.common.RedisCache;
import com.howbuy.cc.basic.cache.constant.CacheConstant;

/**
 * Redis 和spring-Cache集成
 * Created by xinwei.cheng on 2015/5/29.
 */
@SuppressWarnings("unused")
public class EhCache1H extends EhCache {

    final int timeout = 60 * 60;

    @Override
    public String getName() {
        return CacheConstant.EH_CACHE_1H;
    }

    @Override
    public Integer getTimeout() {
        return timeout;
    }
}
