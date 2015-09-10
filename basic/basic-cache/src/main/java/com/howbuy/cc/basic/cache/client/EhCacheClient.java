package com.howbuy.cc.basic.cache.client;

import com.howbuy.cc.basic.cache.constant.CacheCloseConstant;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.logger.CCLogger;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/8/31.
 */
public class EhCacheClient extends CacheClient{

    @Autowired(required = false)
    private Ehcache ehcache;

    CCLogger logger = CCLogger.getLogger(this.getClass());

    @Override
    protected void doPut(String key , Object value, int timeout) {
        Boolean close = Configuration.getBoolean(CacheCloseConstant.EHCACHE_CLOSE);
        if(close != null && close){
            return;
        }
        Element element = new Element(key , value);
        element.setTimeToLive(timeout);
        ehcache.put(element);
    }

    @Override
    protected Object doGet(String key) {
        //关闭缓存开关，如果关闭缓存则不获取直接返回空
        Boolean close = Configuration.getBoolean(CacheCloseConstant.EHCACHE_CLOSE);
        if(close != null && close){
            return null;
        }
        Element element = ehcache.get(key);
        if(element == null){
            return null;
        }
        return element.getObjectValue();
    }

    @Override
    protected void doClear(String key) {
        ehcache.remove(key);
    }

}
