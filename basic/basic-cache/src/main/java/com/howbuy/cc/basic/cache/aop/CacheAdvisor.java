package com.howbuy.cc.basic.cache.aop;

import com.howbuy.cc.basic.cache.annotation.Cache;
import com.howbuy.cc.basic.cache.annotation.CacheClear;
import com.howbuy.cc.basic.cache.namespace.CacheHitOperationSource;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private CacheHitOperationSource cacheHitOperationSource;

    private final Pointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            if(method.isAnnotationPresent(Cache.class) || method.isAnnotationPresent(CacheClear.class)){
                return true;
            }
            return false;
        }
    };


    public Pointcut getPointcut() {
        return this.pointcut;
    }


    public CacheHitOperationSource getCacheHitOperationSource() {
        return cacheHitOperationSource;
    }

    public void setCacheHitOperationSource(CacheHitOperationSource cacheHitOperationSource) {
        this.cacheHitOperationSource = cacheHitOperationSource;
    }
}
