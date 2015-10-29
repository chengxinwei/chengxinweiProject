package com.howbuy.cc.basic.mybatis.datasourceRoute.interceptor;

import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class DynamicDataSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private final Pointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            if(MybatisCommonDao.class.isAssignableFrom(aClass)
                    && (method.isAnnotationPresent(CCDatasourceRoute.class)
                        || aClass.isAnnotationPresent(CCDatasourceRoute.class))){
                return true;
            }
            return false;
        }
    };


    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
