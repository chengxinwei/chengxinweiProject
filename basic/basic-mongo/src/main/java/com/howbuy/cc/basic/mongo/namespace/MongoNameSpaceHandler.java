package com.howbuy.cc.basic.mongo.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class MongoNameSpaceHandler extends NamespaceHandlerSupport {

    private  final static String CORE_DRIVEN = "mongo-driven";

    @Override
    public void init() {
        registerBeanDefinitionParser(CORE_DRIVEN, new MongoBeanDefinitionParser());
    }
}
