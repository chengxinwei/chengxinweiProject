package com.howbuy.cc.basic.cache.aop;

import com.howbuy.cc.basic.cache.client.EhCacheClient;
import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.cache.hit.threadlocal.CacheHitThreadLocal;
import com.howbuy.cc.basic.cache.util.MethodNameUtil;
import javassist.NotFoundException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class SpringCacheInterceptor implements MethodInterceptor, Serializable {

    private final Map<Method,String[]> paramsNameCache = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private RedisClient redisClient;
    @Autowired(required = false)
    private EhCacheClient ehCacheClient;

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Cacheable cacheAble = method.getAnnotation(Cacheable.class);
        CacheEvict cacheClear = method.getAnnotation(CacheEvict.class);

        Object result;
        if(cacheAble != null){
            Class<?> clazz = invocation.getThis().getClass();
            Cacheable cacheAnno  = method.getAnnotation(Cacheable.class);
            String cacheVar = this.getVarKeyByExpr(method , clazz , invocation , cacheAnno.key());

            result = doGetCache(cacheVar , cacheAnno);
            if(result == null) {
                result = invocation.proceed();
                this.doPutCache(result , cacheAnno , cacheVar);
            }
        }else{
            result = invocation.proceed();
        }

        if(cacheClear != null){
            doClearCache(invocation);
        }

        return result;

    }

    private void doClearCache(MethodInvocation invocation){
        Method method = invocation.getMethod();
        Class<?> clazz = invocation.getThis().getClass();
        CacheEvict cacheAnno  = method.getAnnotation(CacheEvict.class);
        String cacheVar = this.getVarKeyByExpr(method , clazz , invocation , cacheAnno.key());
        for(String cacheType : cacheAnno.value()){
           if(cacheType.equals( CacheConstant.REDIS_CACHE_1D) ||
                   cacheType.equals(CacheConstant.REDIS_CACHE_1H) ||
                   cacheType.equals(CacheConstant.REDIS_CACHE_5M) ||
                   cacheType.equals(CacheConstant.REDIS_CACHE_5S)) {
               redisClient.clear("", cacheVar);
           }else{
               ehCacheClient.clear("", cacheVar);
            }
        }

    }


    private Object doGetCache(String key , Cacheable cache) throws Throwable {
        //这里不会记录hit的log 因为hit的log判断了 StringUtils.isEmpty
        //为了兼容老版本 日志记录通过aop实现
        Object obj = null;
        for(String cacheType : cache.value()){
            if(cacheType.equals(CacheConstant.REDIS_CACHE_1D) ||
                    cacheType.equals(CacheConstant.REDIS_CACHE_1H) ||
                    cacheType.equals(CacheConstant.REDIS_CACHE_5M) ||
                    cacheType.equals(CacheConstant.REDIS_CACHE_5S)) {
                obj = redisClient.get("", key);
            }else{
                obj = ehCacheClient.get("", key);
            }
        }
        if(obj == null){
            CacheHitThreadLocal.setHit(false);
        }else{
            CacheHitThreadLocal.setHit(true);
        }
        return obj;
    }


    private void doPutCache(Object result , Cacheable cache ,String keyVar) throws Throwable {
        for(String cacheType : cache.value()){
            if(cacheType.equals(CacheConstant.REDIS_CACHE_1D)){
                int timeout = 60 * 60 * 24;
                redisClient.put("" , keyVar, result , timeout);
            }else if(cacheType.equals(CacheConstant.REDIS_CACHE_1H)){
                int timeout = 60 * 60;
                redisClient.put("" , keyVar, result , timeout);
            }else if(cacheType.equals(CacheConstant.REDIS_CACHE_5M)){
                int timeout = 60 * 5;
                redisClient.put("" , keyVar, result , timeout);
            }else if(cacheType.equals(CacheConstant.REDIS_CACHE_5S)){
                int timeout = 5;
                redisClient.put("" , keyVar, result , timeout);

            }else if(cacheType.equals(CacheConstant.EH_CACHE_1D)){
                int timeout = 60 * 60 * 24;
                ehCacheClient.put("" , keyVar,  result , timeout);
            }else if(cacheType.equals(CacheConstant.EH_CACHE_1H)){
                int timeout = 60 * 60;
                ehCacheClient.put("" , keyVar, result , timeout);
            }else if(cacheType.equals(CacheConstant.EH_CACHE_5M)){
                int timeout = 60 * 5;
                ehCacheClient.put("" , keyVar,  result , timeout);
            }else if(cacheType.equals(CacheConstant.EH_CACHE_5S)){
                int timeout = 5;
                ehCacheClient.put("" , keyVar, result , timeout);
            }
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
                String[] paramsNameAry = MethodNameUtil.getMethodParamNames(clazz , method);
                paramsNameCache.put(method , paramsNameAry);
                return paramsNameAry;
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