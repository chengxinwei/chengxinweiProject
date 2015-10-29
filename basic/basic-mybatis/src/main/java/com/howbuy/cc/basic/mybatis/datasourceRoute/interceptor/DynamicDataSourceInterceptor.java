package com.howbuy.cc.basic.mybatis.datasourceRoute.interceptor;

import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.datasourceRoute.DynamicDataSource;
import com.howbuy.cc.basic.mybatis.datasourceRoute.DynamicDataSourceSwitch;
import com.howbuy.cc.basic.spring.SpringBean;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class DynamicDataSourceInterceptor implements MethodInterceptor, Serializable {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        if(SpringBean.getBeans(DynamicDataSource.class).isEmpty()){
            return invocation.proceed();
        }

        Method method = invocation.getMethod();
        CCDatasourceRoute ccDatasourceRoute = method.isAnnotationPresent(CCDatasourceRoute.class) ?
                method.getAnnotation(CCDatasourceRoute.class) :
                invocation.getThis().getClass().getAnnotation(CCDatasourceRoute.class);
        DynamicDataSourceSwitch.setDataSource(ccDatasourceRoute.value());
        Object result = invocation.proceed();
        DynamicDataSourceSwitch.setDataSource(null);
        return result;
    }

}