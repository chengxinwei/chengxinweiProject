package com.howbuy.cc.basic.failover.aop;

import com.howbuy.cc.basic.failover.annation.FailOver;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class FailOverAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private final Pointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            if (method.isAnnotationPresent(FailOver.class)) {
                return true;
            }
            return false;
        }
    };


    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
