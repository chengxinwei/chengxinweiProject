package com.howbuy.cc.basic.mybatis.aop;

import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.datasourceRoute.DynamicDataSourceSwitch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * dao层的aop用户确定数据源切换
 * Created by xinwei.cheng on 2015/7/16.
 */
@Aspect
@Component
public class DynamicDataSourceAspect {


    @Pointcut("execution(* com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao.*(..))")
    public void mybatisCommonDao(){}

    @Before("mybatisCommonDao()")
    public void before(JoinPoint joinPoint){
        Class<?> clazz = joinPoint.getTarget().getClass();
        if(clazz.isAnnotationPresent(CCDatasourceRoute.class)){
            CCDatasourceRoute ccDatasourceRoute = clazz.getAnnotation(CCDatasourceRoute.class);
            String datasourceName = ccDatasourceRoute.value();
            DynamicDataSourceSwitch.setDataSource(datasourceName);
        }
    }
}
