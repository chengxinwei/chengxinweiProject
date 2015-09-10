package com.howbuy.cc.basic.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheClear {

    String cacheName() ;

    String cacheExpr() ;

    Cache.CacheType cacheType() default Cache.CacheType.redis ;

    boolean beforeExecute() default false;

}
