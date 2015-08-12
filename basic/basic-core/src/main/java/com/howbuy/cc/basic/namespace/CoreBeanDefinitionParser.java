package com.howbuy.cc.basic.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CoreBeanDefinitionParser implements org.springframework.beans.factory.xml.BeanDefinitionParser {

    private final static String SPRING_CORE_XML = "classpath:basic/spring/base-spring.xml";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        parserContext.getReaderContext().getReader().loadBeanDefinitions(SPRING_CORE_XML);
        return null;
    }

}
