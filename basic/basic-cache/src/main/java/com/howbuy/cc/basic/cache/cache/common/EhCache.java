package com.howbuy.cc.basic.cache.cache.common;

import com.howbuy.cc.basic.cache.constant.CacheCloseConstant;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import com.howbuy.cc.basic.config.Configuration;
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

    CCLogger logger = CCLogger.getLogger(this.getClass());

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
            //关闭缓存开关，如果关闭缓存则不获取直接返回空
            Boolean close = Configuration.getBoolean(CacheCloseConstant.EHCACHE_CLOSE);
            if(close != null && close){
                return null;
            }
            element = ehcache.get(CacheKeyGenerator.getKeyStr(String.valueOf(key)));
        }catch(Exception e){
            logger.error(e.getMessage() , e);
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
            logger.error(e.getMessage() , e);
        }
    }

    public void evict(Object key) {
        try {
            ehcache.remove(CacheKeyGenerator.getKeyStr(String.valueOf(key)));
        }catch(Exception e){
            logger.error(e.getMessage() , e);
        }

    }

    public void clear() {
        throw new RuntimeException("not support");
    }
}

