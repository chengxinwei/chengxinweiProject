package com.howbuy.cc.basic.cache.namespace;

import com.howbuy.cc.basic.cache.aop.CacheAdvisor;
import com.howbuy.cc.basic.cache.aop.CacheInterceptor;
import com.howbuy.cc.basic.cache.aop.SpringCacheAdvisor;
import com.howbuy.cc.basic.cache.aop.SpringCacheInterceptor;
import com.howbuy.cc.basic.cache.client.EhCacheClient;
import com.howbuy.cc.basic.cache.client.RedisClient;
import com.howbuy.cc.basic.cache.failover.EHCacheFailOverHandler;
import com.howbuy.cc.basic.cache.failover.RedisCacheFailOverHandler;
import com.howbuy.cc.basic.cache.hit.aop.CacheHitAdvisor;
import com.howbuy.cc.basic.cache.hit.aop.CacheHitInterceptor;
import com.howbuy.cc.basic.cache.hit.thread.CacheHitLogThread;
import com.howbuy.tp.common.redis.core.JedisSentinelShardedConfig;
import com.howbuy.tp.common.redis.serializer.ObjectSerializer;
import com.howbuy.tp.common.redis.serializer.StringRedisSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;


/**
 * 缓存启动配置类
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CacheBeanDefinitionParser implements BeanDefinitionParser {


    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);

        parseCacheAop(element , parserContext);

        parseSpringCacheAop(element, parserContext);

        List<Element> childElts = DomUtils.getChildElements(element);
        for (Element elt: childElts) {
            String localName = parserContext.getDelegate().getLocalName(elt);
            if ("redis".equals(localName)) {
                parseRedis(elt, parserContext);
            }else if("ehcache".equals(localName)){
                parseEhcache(elt , parserContext);
            }
        }
        //cache hit
        this.parseCacheHit(element , parserContext);
        parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(EHCacheFailOverHandler.class));
        parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(RedisCacheFailOverHandler.class));
        return null;
    }

    /**
     * 配置redis
     */
    private void parseRedis(Element element, ParserContext parserContext){
        String sentinels = element.getAttribute("sentinels");
        String serverName = element.getAttribute("serverName");
        String maxIdle = element.getAttribute("maxIdle");
        String maxTotal = element.getAttribute("maxTotal");
        String minIdle = element.getAttribute("minIdle");
        String keyLimit = element.getAttribute("keyLimit");
        String valueLimit = element.getAttribute("valueLimit");
        String dbNum = element.getAttribute("dbNum");

        RootBeanDefinition objectSerializerBeanDefinition = new RootBeanDefinition(ObjectSerializer.class);
        String objectSerializerBeanName = parserContext.getReaderContext().registerWithGeneratedName(objectSerializerBeanDefinition);

        RootBeanDefinition stringSerializerBeanDefinition = new RootBeanDefinition(StringRedisSerializer.class);
        String stringtSerializerBeanName = parserContext.getReaderContext().registerWithGeneratedName(stringSerializerBeanDefinition);

        RootBeanDefinition redisBeanDefinition = new RootBeanDefinition(JedisSentinelShardedConfig.class);
        redisBeanDefinition.getPropertyValues().add("sentinels" , sentinels);
        redisBeanDefinition.getPropertyValues().add("masters" , serverName);
        redisBeanDefinition.getPropertyValues().add("maxIdle" , maxIdle);
        redisBeanDefinition.getPropertyValues().add("maxTotal" , maxTotal);
        redisBeanDefinition.getPropertyValues().add("minIdle" , minIdle);
        redisBeanDefinition.getPropertyValues().add("keyLimit" , keyLimit);
        redisBeanDefinition.getPropertyValues().add("valueLimit" , valueLimit);
        redisBeanDefinition.getPropertyValues().add("dbNum" , dbNum);
        redisBeanDefinition.getPropertyValues().add("keySerializer" , new RuntimeBeanReference(stringtSerializerBeanName));
        redisBeanDefinition.getPropertyValues().add("valueSerializer" , new RuntimeBeanReference(objectSerializerBeanName));
        redisBeanDefinition.setInitMethodName("init");
        parserContext.getReaderContext().registerWithGeneratedName(redisBeanDefinition);

        parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(RedisClient.class));

    }


//    /**
//     * PA版本1.3使用
//     * @param element
//     * @param parserContext
//     */
//    private void parseRedis2(Element element, ParserContext parserContext){
//        String sentinels = element.getAttribute("sentinels");
//        String serverName = element.getAttribute("serverName");
//        String maxIdle = element.getAttribute("maxIdle");
//        String maxTotal = element.getAttribute("maxTotal");
//        String minIdle = element.getAttribute("minIdle");
//        String keyLimit = element.getAttribute("keyLimit");
//        String valueLimit = element.getAttribute("valueLimit");
//        String dbNum = element.getAttribute("dbNum");
//
//
//        RootBeanDefinition rcBeanDefinition = new RootBeanDefinition(RedisConfig.class);
//        rcBeanDefinition.getPropertyValues().add("sentinels" , sentinels);
//        rcBeanDefinition.getPropertyValues().add("masters" , serverName);
//        rcBeanDefinition.getPropertyValues().add("maxIdle" , maxIdle);
//        rcBeanDefinition.getPropertyValues().add("maxTotal" , maxTotal);
//        rcBeanDefinition.getPropertyValues().add("dbNum" , dbNum);
//        rcBeanDefinition.getPropertyValues().add("minIdle" , minIdle);
//        rcBeanDefinition.getPropertyValues().add("keyLimit" , keyLimit);
//        rcBeanDefinition.getPropertyValues().add("valueLimit" , valueLimit);
//        rcBeanDefinition.getPropertyValues().add("valueLimit" , valueLimit);
//        rcBeanDefinition.getPropertyValues().add("deployType" , JedisDeployType.SENTINEL.name());
////         rc.setTimeout(2000); //todo
//        String rcBeanName = parserContext.getReaderContext().registerWithGeneratedName(rcBeanDefinition);
//
//        RootBeanDefinition rcfBeanDefinition = new RootBeanDefinition(JedisConfigFactory.class);
//        rcfBeanDefinition.getPropertyValues().add("defaultRedisConfig" , new RuntimeBeanReference(rcBeanName));
//        rcfBeanDefinition.setInitMethodName("init");
//        parserContext.getReaderContext().registerWithGeneratedName(rcfBeanDefinition);
//
//        parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(RedisClient.class));
//    }


    public void parseCacheAop(Element element, ParserContext parserContext){
        Object eleSource = parserContext.extractSource(element);

        // Create the CacheInterceptor definition.
        RootBeanDefinition interceptorDef = new RootBeanDefinition(CacheInterceptor.class);
        interceptorDef.setSource(eleSource);
        interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

        // Create the CacheAdvisor definition.
        RootBeanDefinition advisorDef = new RootBeanDefinition(CacheAdvisor.class);
        advisorDef.setSource(eleSource);
        advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
        advisorDef.getPropertyValues().add("order" , Integer.MAX_VALUE);
        String cacheAdvisorBeanName = parserContext.getReaderContext().registerWithGeneratedName(advisorDef);

        CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName() , eleSource);
        compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
        compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, cacheAdvisorBeanName));
        parserContext.registerComponent(compositeDef);

    }

    public void parseSpringCacheAop(Element element, ParserContext parserContext){

        Object eleSource = parserContext.extractSource(element);

        // Create the CacheInterceptor definition.
        RootBeanDefinition interceptorDef = new RootBeanDefinition(SpringCacheInterceptor.class);
        interceptorDef.setSource(eleSource);
        interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

        // Create the CacheAdvisor definition.
        RootBeanDefinition advisorDef = new RootBeanDefinition(SpringCacheAdvisor.class);
        advisorDef.setSource(eleSource);
        advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
        advisorDef.getPropertyValues().add("order" , Integer.MAX_VALUE);
        String cacheAdvisorBeanName = parserContext.getReaderContext().registerWithGeneratedName(advisorDef);

        CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName() , eleSource);
        compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
        compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, cacheAdvisorBeanName));
        parserContext.registerComponent(compositeDef);

    }


    /**
     * 配置ehcache
     */
    private void parseEhcache(Element element, ParserContext parserContext){

        String maxElementsInMemory = element.getAttribute("maxElementsInMemory");

        //ehcacheManager
        String ehcacheManagerBeanName = parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(EhCacheManagerFactoryBean.class));
        //ehcacheFactory
        RootBeanDefinition ehcacheFactoryBeanDefinition = new RootBeanDefinition(EhCacheFactoryBean.class);
        ehcacheFactoryBeanDefinition.getPropertyValues().addPropertyValue("cacheManager" , new RuntimeBeanReference(ehcacheManagerBeanName));
        ehcacheFactoryBeanDefinition.getPropertyValues().addPropertyValue("overflowToDisk" , false);
        ehcacheFactoryBeanDefinition.getPropertyValues().addPropertyValue("maxElementsInMemory" , maxElementsInMemory);
        ehcacheFactoryBeanDefinition.getPropertyValues().addPropertyValue("cacheName" , "ehCache");
        parserContext.getReaderContext().registerWithGeneratedName(ehcacheFactoryBeanDefinition);

        //ehcacheClient
        parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(EhCacheClient.class));
    }


    /**
     * 配置命中率
     */
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
                sourceDef.getPropertyValues().add("hitLogTime", hitLogTime);
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
