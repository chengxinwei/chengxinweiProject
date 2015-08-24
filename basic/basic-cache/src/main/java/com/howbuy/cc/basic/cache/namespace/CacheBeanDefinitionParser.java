package com.howbuy.cc.basic.cache.namespace;

import com.howbuy.cc.basic.cache.hit.aop.CacheHitAdvisor;
import com.howbuy.cc.basic.cache.hit.aop.CacheHitInterceptor;
import com.howbuy.cc.basic.cache.hit.thread.CacheHitLogThread;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
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

        //cache hit
        this.parseCacheHit(element , parserContext);
        return null;
    }


    private void parseCacheHit(Element element, ParserContext parserContext){
        if(element.hasAttribute("hitLog")) {

            String hitLogPath = element.getAttribute("hitLog");
            String hitLogTime = element.getAttribute("hitLogTime");

            Object eleSource = parserContext.extractSource(element);

            // Create the CacheOperationSource definition.
            RootBeanDefinition sourceDef = new RootBeanDefinition(CacheHitOperationSource.class);
            sourceDef.setSource(eleSource);
            sourceDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            sourceDef.getPropertyValues().add("hitLogPath" , hitLogPath);
            if(!StringUtils.isEmpty(hitLogTime)) {
                sourceDef.getPropertyValues().add("hitLogTime", Integer.parseInt(hitLogTime));
            }
            String sourceName = parserContext.getReaderContext().registerWithGeneratedName(sourceDef);

            // Create the CacheInterceptor definition.
            RootBeanDefinition interceptorDef = new RootBeanDefinition(CacheHitInterceptor.class);
            interceptorDef.setSource(eleSource);
            interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            interceptorDef.getPropertyValues().add("cacheHitOperationSource", new RuntimeBeanReference(sourceName));
            String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

            // Create the CacheAdvisor definition.
            RootBeanDefinition advisorDef = new RootBeanDefinition(CacheHitAdvisor.class);
            advisorDef.setSource(eleSource);
            advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
            advisorDef.getPropertyValues().add("order" , 0);
            parserContext.getRegistry().registerBeanDefinition(CacheHitAdvisor.class.getName(), advisorDef);

            //create hitlog thread
            RootBeanDefinition hitLogThread = new RootBeanDefinition(CacheHitLogThread.class);
            hitLogThread.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            hitLogThread.setInitMethodName("init");
            hitLogThread.getPropertyValues().add("cacheHitOperationSource", new RuntimeBeanReference(sourceName));
            parserContext.getReaderContext().registerWithGeneratedName(hitLogThread);

            CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName() , eleSource);
            compositeDef.addNestedComponent(new BeanComponentDefinition(sourceDef, sourceName));
            compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
            compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, CacheHitAdvisor.class.getName()));
            parserContext.registerComponent(compositeDef);

        }
    }
}
