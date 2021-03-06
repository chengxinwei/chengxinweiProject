package com.howbuy.cc.basic.cache.failover;

import com.howbuy.cc.basic.cache.client.CacheClient;
import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.failover.annation.FailOver;
import com.howbuy.cc.basic.failover.handler.common.FailOverHandler;
import com.howbuy.cc.basic.spring.SpringBean;
import org.nutz.json.Json;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/9/28.
 */
public class RedisCacheFailOverHandler implements FailOverHandler {

    @Override
    public Object handlerFailOver(FailOver failOver , Class<?> targetClass , Method method , Object[] args) {
        String cacheName = "failOverCache." + targetClass.getSimpleName() + "." + method.getName();
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < args.length ; i ++){
            sb.append(Json.toJson(args)).append(",");
        }
        CacheClient cacheClient = SpringBean.getBean(RedisClient.class);
        return cacheClient.get(cacheName , sb.toString());
    }

    @Override
    public void setFailOverValue(FailOver failOver, Class<?> targetClass, Method method, Object[] args, Object object) {
        String cacheName = "failOverCache." + targetClass.getSimpleName() + "." + method.getName();
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < args.length ; i ++){
            sb.append(Json.toJson(args)).append(",");
        }
        SpringBean.getBean(RedisClient.class).put(cacheName, sb.toString(), object , failOver.failOverCacheTimeout());

    }
}
