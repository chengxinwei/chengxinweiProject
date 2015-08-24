package com.howbuy.cc.basic.namespace;

import com.howbuy.cc.basic.config.PropertyPlaceHolderResolver;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class CoreBeanDefinitionParser implements org.springframework.beans.factory.xml.BeanDefinitionParser {



    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
//        parserContext.getReaderContext().getReader().loadBeanDefinitions(SPRING_CORE_XML);

        /**
         *  <bean class="com.howbuy.cc.basic.config.PropertyPlaceHolderResolver"/>
         *  <bean class="com.howbuy.cc.basic.spring.SpringBean"></bean>
         */

        RootBeanDefinition placeHolderBeanDefinition = new RootBeanDefinition(PropertyPlaceHolderResolver.class);
        parserContext.getReaderContext().registerWithGeneratedName(placeHolderBeanDefinition);

        RootBeanDefinition springBeanDefinition = new RootBeanDefinition(SpringBean.class);
        parserContext.getReaderContext().registerWithGeneratedName(springBeanDefinition);


        RootBeanDefinition sourceDefinition = new RootBeanDefinition(CoreOperationSource.class);

        List<Element> childElts = DomUtils.getChildElements(element);
        for (Element elt: childElts) {
            String localName = parserContext.getDelegate().getLocalName(elt);
            if ("dubboLog".equals(localName)) {
                parseDubboLog(elt, sourceDefinition);
            }
        }
        parserContext.getReaderContext().registerWithGeneratedName(sourceDefinition);
        return null;
    }


    private void parseDubboLog(Element element, RootBeanDefinition sourceDefinition){
        String accessLog = element.getAttribute("accessLog");
        String requestLog = element.getAttribute("requestLog");
        sourceDefinition.getPropertyValues().add("accessLog" , accessLog);
        sourceDefinition.getPropertyValues().add("requestLog" , requestLog);

    }
}
