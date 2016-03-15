package com.howbuy.cc.basic.namespace;

import com.howbuy.cc.basic.config.PropertyPlaceHolderResolver;
import com.howbuy.cc.basic.config.PropertyPlaceHolderResolverForPa;
import com.howbuy.cc.basic.failover.aop.FailOverAdvisor;
import com.howbuy.cc.basic.failover.aop.FailOverInterceptor;
import com.howbuy.cc.basic.failover.handler.AbandonFailOverHandler;
import com.howbuy.cc.basic.filter.FilterExcludeBeanPostProcessor;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.Ordered;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CoreBeanDefinitionParser implements org.springframework.beans.factory.xml.BeanDefinitionParser {



    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        boolean useConfigServer = Boolean.valueOf(element.getAttribute("useConfigServer"));
        if(useConfigServer) {
            parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(PropertyPlaceHolderResolverForPa.class));
        }else {
            parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(PropertyPlaceHolderResolver.class));
        }
        parserContext.getReaderContext().registerWithGeneratedName( new RootBeanDefinition(SpringBean.class));

        RootBeanDefinition sourceDefinition = new RootBeanDefinition(CoreOperationSource.class);

        sourceDefinition.getPropertyValues().add("useConfigServer" , useConfigServer);

        List<Element> childElts = DomUtils.getChildElements(element);
        for (Element elt: childElts) {
            String localName = parserContext.getDelegate().getLocalName(elt);
            if ("dubboLog".equals(localName)) {
                parseDubboLog(elt, sourceDefinition , parserContext);
            }else if("failover".equals(localName)){
                registFailOverAop(elt , parserContext , sourceDefinition);
            }
        }
        parserContext.getReaderContext().registerWithGeneratedName(sourceDefinition);

        return null;
    }


    /**
     * 注册dubbolog
     * 为sourceDefinition组装对象，放入accessLog requestLog excludeLogDetail
     * 拼装完之后不注册，交给调用段注册
     * @param element
     * @param sourceDefinition
     */
    private void parseDubboLog(Element element, RootBeanDefinition sourceDefinition ,  ParserContext parserContext){
        String accessLog = element.getAttribute("accessLog");
        String requestLog = element.getAttribute("requestLog");
        String excludeLogDetail = element.getAttribute("excludeLogDetail");
        String autoFilter = element.getAttribute("autoFilter");

        if(Boolean.valueOf(autoFilter)) {
            parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(FilterExcludeBeanPostProcessor.class));
        }
        sourceDefinition.getPropertyValues().add("accessLog" , accessLog);
        sourceDefinition.getPropertyValues().add("requestLog" , requestLog);
        sourceDefinition.getPropertyValues().add("excludeLogDetail" , excludeLogDetail);
    }


    /**
     * 注册failover的aop和相关默认的handler
     */
    private void registFailOverAop(Element element, ParserContext parserContext , RootBeanDefinition sourceDefinition){
        sourceDefinition.getPropertyValues().add("failOverLog" , element.getAttribute("failOverLog"));

        RootBeanDefinition interceptorDef = new RootBeanDefinition(FailOverInterceptor.class);
        interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

        RootBeanDefinition advisorDef = new RootBeanDefinition(FailOverAdvisor.class);
        advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
        parserContext.getRegistry().registerBeanDefinition(FailOverAdvisor.class.getName(), advisorDef);

        parserContext.getReaderContext().registerWithGeneratedName(new RootBeanDefinition(AbandonFailOverHandler.class));
       }
}
