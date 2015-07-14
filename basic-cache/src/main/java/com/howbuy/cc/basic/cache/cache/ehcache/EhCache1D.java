package com.howbuy.cc.basic.cache.cache.ehcache;

import com.howbuy.cc.basic.cache.cache.common.EhCache;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * EH 集成spring-cache
 * Created by xinwei.cheng on 2015/7/7.
 */
public class EhCache1D extends EhCache {

    final int timeout = 60 * 60 *24;

    @Override
    public String getName() {
        return CacheConstant.EH_CACHE_1D;
    }

    @Override
    public Integer getTimeout() {
        return timeout;
    }
}

