package com.howbuy.cc.basic.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cache {

    CacheType cacheType() default CacheType.redis ;

    String cacheName() ;

    String cacheExpr() ;

    int timeout() default 60 * 60;

    public static enum CacheType{
        ehcache , redis
    }

}
