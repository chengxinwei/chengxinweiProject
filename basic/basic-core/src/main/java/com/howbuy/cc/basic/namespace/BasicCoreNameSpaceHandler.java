package com.howbuy.cc.basic.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class BasicCoreNameSpaceHandler extends NamespaceHandlerSupport {

    private  final static String CORE_DRIVEN = "core-driven";

    public void init() {
        registerBeanDefinitionParser(CORE_DRIVEN, new CoreBeanDefinitionParser());
    }
}
