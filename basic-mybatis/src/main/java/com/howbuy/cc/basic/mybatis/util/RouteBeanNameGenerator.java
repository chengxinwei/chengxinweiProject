package com.howbuy.cc.basic.mybatis.util;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
public final class RouteBeanNameGenerator {

    private RouteBeanNameGenerator(){}

    public final static String getBeanName(String datasourceName){
        return "sqlSessionRoute-" + datasourceName;
    }


}
