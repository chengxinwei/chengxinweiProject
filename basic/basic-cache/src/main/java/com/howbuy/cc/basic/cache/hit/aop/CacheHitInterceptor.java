package com.howbuy.cc.basic.cache.hit.aop;

import com.howbuy.cc.basic.cache.hit.HitCount;
import com.howbuy.cc.basic.cache.hit.threadlocal.CacheHitThreadLocal;
import com.howbuy.cc.basic.cache.namespace.CacheHitOperationSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheHitInterceptor implements MethodInterceptor, Serializable {

    private CacheHitOperationSource cacheHitOperationSource;

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        String className = invocation.getThis().getClass().getSimpleName();
        String fullName = className + "." + invocation.getMethod().getName();
        Object obj = invocation.proceed();
        if(CacheHitThreadLocal.isHit()){
            HitCount.incrHit(fullName);
        }else{
            HitCount.incrMiss(fullName);
        }
        return obj;
    }

    public CacheHitOperationSource getCacheHitOperationSource() {
        return cacheHitOperationSource;
    }

    public void setCacheHitOperationSource(CacheHitOperationSource cacheHitOperationSource) {
        this.cacheHitOperationSource = cacheHitOperationSource;
    }
}
