package com.howbuy.cc.basic.failover.annation;

import com.howbuy.cc.basic.failover.handler.AbandonFailOverHandler;
import com.howbuy.cc.basic.failover.handler.common.FailOverHandler;

import java.lang.annotation.*;

/**
 * Created by xinwei.cheng on 2015/9/28.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@SuppressWarnings("unused")
public @interface FailOver {

    //方法执行最大线程数
    int maxThreadCount() default 10;
    //方法执行最大耗时ms
    int maxExecuteTimeout() default 500;

    int failOverCacheTimeout() default 60 * 60 ;

    Class<? extends FailOverHandler> handlerClass() default FailOverHandler.class;

}
