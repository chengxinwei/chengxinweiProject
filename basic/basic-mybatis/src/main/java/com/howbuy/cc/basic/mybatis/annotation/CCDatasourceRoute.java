package com.howbuy.cc.basic.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 数据源路由
 * Created by xinwei.cheng on 2015/7/8.
 */
@Target({ElementType.TYPE , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CCDatasourceRoute {

    String value();

}
