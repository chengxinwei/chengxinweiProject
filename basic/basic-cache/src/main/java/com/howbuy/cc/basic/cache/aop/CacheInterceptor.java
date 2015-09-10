package com.howbuy.cc.basic.cache.aop;

import com.howbuy.cc.basic.cache.annotation.Cache;
import com.howbuy.cc.basic.cache.annotation.CacheClear;
import com.howbuy.cc.basic.cache.client.EhCacheClient;
import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.cache.util.MethodNameUtil;
import javassist.NotFoundException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class CacheInterceptor implements MethodInterceptor, Serializable {

    private final Map<Method,String[]> paramsNameCache = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private RedisClient redisClient;
    @Autowired(required = false)
    private EhCacheClient ehCacheClient;

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        CacheClear cacheClear = method.getAnnotation(CacheClear.class);
        Cache cache = method.getAnnotation(Cache.class);

        if(cacheClear != null && cacheClear.beforeExecute()){
            doClearCache(invocation);
        }

        Object result;
        if(cache != null){
            Class<?> clazz = invocation.getThis().getClass();
            Cache cacheAnno  = method.getAnnotation(Cache.class);
            String cacheVar = this.getVarKeyByExpr(method , clazz , invocation , cacheAnno.cacheExpr());

            result = doGetCache(cacheVar , cacheAnno);
            if(result == null) {
                result = invocation.proceed();
                this.doPutCache(result , cacheAnno , cacheVar);
            }
        }else{
            result = invocation.proceed();
        }

        if(cacheClear != null && !cacheClear.beforeExecute()){
            doClearCache(invocation);
        }

        return result;

    }

    private void doClearCache(MethodInvocation invocation){
        Method method = invocation.getMethod();
        Class<?> clazz = invocation.getThis().getClass();
        CacheClear cacheAnno  = method.getAnnotation(CacheClear.class);
        String cacheVar = this.getVarKeyByExpr(method , clazz , invocation , cacheAnno.cacheExpr());
        switch (cacheAnno.cacheType()){
            case redis:
                redisClient.clear(cacheAnno.cacheName(), cacheVar);
                break;
            case ehcache:
                ehCacheClient.clear(cacheAnno.cacheName(), cacheVar);
                break;
        }

    }


    private Object doGetCache(String key , Cache cache) throws Throwable {
        switch (cache.cacheType()){
            case redis:
                return redisClient.get(cache.cacheName(), key);
            case ehcache:
                return ehCacheClient.get(cache.cacheName() , key);
        }
        return null;
    }


    private void doPutCache(Object result , Cache cache ,String keyVar) throws Throwable {

        switch (cache.cacheType()){
            case redis:
                redisClient.put(cache.cacheName(), keyVar, result, cache.timeout());
                break;
            case ehcache:
                ehCacheClient.put(cache.cacheName() , keyVar , result , cache.timeout());
                break;
        }
    }

    /**
     * 根据表达式获取key
     * @param method
     * @param clazz
     * @param invocation
     * @param expr
     * @return
     */
    private String getVarKeyByExpr( Method method , Class<?> clazz , MethodInvocation invocation , String expr){
        String[] paramsNameAry = getMethodNameAry(method , clazz);
        Map<String , Object> params = new HashMap<>();
        for(int i = 0 ; i < paramsNameAry.length ; i ++){
            params.put(paramsNameAry[i] , invocation.getArguments()[i]);
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(params);
        return spelExpressionParser.parseExpression(expr).getValue(context , String.class);
    }


    private String[] getMethodNameAry(Method method , Class<?> clazz){
        if(paramsNameCache.containsKey(method)){
            return paramsNameCache.get(method);
        }else{
            try {
                return MethodNameUtil.getMethodParamNames(clazz , method);
            } catch (NotFoundException e) {
                return new String[0];
            }
        }
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public EhCacheClient getEhCacheClient() {
        return ehCacheClient;
    }

    public void setEhCacheClient(EhCacheClient ehCacheClient) {
        this.ehCacheClient = ehCacheClient;
    }
}