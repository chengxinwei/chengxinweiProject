package com.howbuy.cc.basic.mongo.mongoTime;

import com.howbuy.cc.basic.mongo.dao.MongoCommonDao;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/21.
 */
public class MongoTimeAdvisor  extends AbstractBeanFactoryPointcutAdvisor {

    private final Pointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> clazz) {
            if(MongoCommonDao.class.isAssignableFrom(clazz)){
                List<Method> methodList = Arrays.asList(MongoCommonDao.class.getMethods());
                if(methodList.contains(method)){
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
