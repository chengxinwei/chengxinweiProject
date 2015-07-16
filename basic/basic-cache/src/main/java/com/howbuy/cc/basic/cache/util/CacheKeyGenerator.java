package com.howbuy.cc.basic.cache.util;

import com.howbuy.cc.basic.config.Configuration;

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
    public static byte[] getKeyByteAry (String key){
        if(key == null){
            return null;
        }
        String namespace = Configuration.getNamespace();
        return (namespace + key).getBytes();
    }

    public static String getKeyStr(String key){
        if(key == null){
            return null;
        }
        String namespace = Configuration.getNamespace();
        return namespace + key;
    }

}
