package com.howbuy.cc.basic.cache.hit.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheHitAdvisor  extends AbstractBeanFactoryPointcutAdvisor {

    private CacheHitOperationSource cacheHitOperationSource;

    private final Pointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            if(method.isAnnotationPresent(Cacheable.class)){
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
