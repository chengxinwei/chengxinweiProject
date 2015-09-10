package com.howbuy.cc.basic.cache.util;

import com.howbuy.cc.basic.config.Configuration;
import org.apache.commons.lang3.StringUtils;

/**
 * 缓存key生成器
 * Created by xinwei.cheng on 2015/7/7.
 */
public class CacheKeyGenerator {

    /**
     * 获取key的byte数组 用户保存序列化的对象
     * @param key 客户key
     * @return 生成key
     */
    @Deprecated
    public static String getKeyStr(String key){
        if(key == null){
            return null;
        }
        String namespace = getNameSpace();
        return namespace + "." + key;
    }

    public static String getKeyStr(String cacheName , String cacheVar){
        return CacheKeyGenerator.getKeyStr(StringUtils.isNotEmpty(cacheName) ? (cacheName + "." + cacheVar) : cacheVar);
    }


    public static String getNameSpace(){
        return Configuration.get("cache.application.name") == null ? Configuration.getDefaultApplicationName() : Configuration.get("cache.application.name");
    }

}
