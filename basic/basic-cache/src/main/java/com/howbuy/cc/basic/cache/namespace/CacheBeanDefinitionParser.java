package com.howbuy.cc.basic.cache.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheBeanDefinitionParser implements BeanDefinitionParser {

    private final static String SPRING_CACHE_XML = "classpath:basic/spring/cache/spring-cache.xml";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        parserContext.getReaderContext().getReader().loadBeanDefinitions(SPRING_CACHE_XML);
        return null;
    }

}
