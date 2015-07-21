package com.howbuy.cc.basic.cache.cache.common;

import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import com.howbuy.cc.basic.logger.CCLogger;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * EH 集成spring-cache
 * Created by xinwei.cheng on 2015/7/7.
 */
public abstract class EhCache implements Cache {

    @Autowired
    private Ehcache ehcache;

    @Override
    public abstract String getName();
    public abstract Integer getTimeout();

    public Object getNativeCache() {
        return ehcache;
    }

    public ValueWrapper get(Object key) {
        Element element = null;
        try {
            element = ehcache.get(CacheKeyGenerator.getKeyStr(String.valueOf(key)));
        }catch(Exception e){
            CCLogger.error(CacheConstant.EHCACHE_ERROR , e.getMessage() , e);
        }
        if (element == null) {
            return null;
        }
        return new SimpleValueWrapper(element.getObjectValue());
    }

    public void put(Object key, Object value) {
        try {
            Element element = new Element(CacheKeyGenerator.getKeyStr(String.valueOf(key)) , value);
            element.setTimeToLive(getTimeout());
            ehcache.put(element);
        }catch(Exception e){
            CCLogger.error(CacheConstant.EHCACHE_ERROR , e.getMessage() , e);
        }
    }

    public void evict(Object key) {
        try {
            ehcache.remove(CacheKeyGenerator.getKeyStr(String.valueOf(key)));
        }catch(Exception e){
            CCLogger.error(CacheConstant.EHCACHE_ERROR , e.getMessage() , e);
        }

    }

    public void clear() {
        throw new RuntimeException("not support");
    }
}

