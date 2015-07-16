package com.howbuy.cc.basic.mybatis.annotation;

import java.lang.annotation.*;

/**
 * mybatis的Mapper命名空间
 * Created by xinwei.cheng on 2015/7/16.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CCNameSpaceMapper {

    String value();

}
