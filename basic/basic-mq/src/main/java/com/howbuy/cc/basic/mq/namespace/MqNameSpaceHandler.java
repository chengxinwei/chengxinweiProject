package com.howbuy.cc.basic.mq.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
@SuppressWarnings("unused")
public class MqNameSpaceHandler extends NamespaceHandlerSupport {

    private  final static String MQ_DRIVEN = "mq-driven";

    public void init() {
        registerBeanDefinitionParser(MQ_DRIVEN , new MqBeanDefinitionParser());
    }
}
