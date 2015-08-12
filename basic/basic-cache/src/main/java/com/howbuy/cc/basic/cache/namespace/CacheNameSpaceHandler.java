package com.howbuy.cc.basic.cache.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheNameSpaceHandler extends NamespaceHandlerSupport {

    private  final static String CACHE_DRIVEN = "cache-driven";

    public void init() {
        registerBeanDefinitionParser(CACHE_DRIVEN , new CacheBeanDefinitionParser());
    }
}
