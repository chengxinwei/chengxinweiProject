package com.howbuy.cc.basic.mybatis.namespace;

import com.howbuy.cc.basic.namespace.CoreBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class MybatisNameSpaceHandler extends NamespaceHandlerSupport {

    private  final static String CORE_DRIVEN = "mybatis-driven";

    public void init() {
        registerBeanDefinitionParser(CORE_DRIVEN, new MybatisBeanDefinitionParser());
    }
}
