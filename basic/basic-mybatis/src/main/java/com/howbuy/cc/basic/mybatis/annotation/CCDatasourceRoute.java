package com.howbuy.cc.basic.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 数据源路由
 * Created by xinwei.cheng on 2015/7/8.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CCDatasourceRoute {

    String value();

}
