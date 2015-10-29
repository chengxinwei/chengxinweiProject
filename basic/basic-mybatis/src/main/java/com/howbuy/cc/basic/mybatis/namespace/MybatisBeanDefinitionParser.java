package com.howbuy.cc.basic.mybatis.namespace;

import com.howbuy.cc.basic.mybatis.datasourceRoute.interceptor.DynamicDataSourceAdvisor;
import com.howbuy.cc.basic.mybatis.datasourceRoute.interceptor.DynamicDataSourceInterceptor;
import com.howbuy.cc.basic.mybatis.interceptor.SqlLoggerInterceptor;
import org.springframework.aop.config.AopNamespaceUtils;
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
public class MybatisBeanDefinitionParser implements BeanDefinitionParser {



    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String sqlTimeLog = element.getAttribute("sqlTimeLog");
        String sqlTimeout = element.getAttribute("sqlTimeout");

        RootBeanDefinition mybatisSourceBeanDefinition = new RootBeanDefinition(MybatisOperationSource.class);
        mybatisSourceBeanDefinition.getPropertyValues().add("sqlTimeLog" , sqlTimeLog);
        mybatisSourceBeanDefinition.getPropertyValues().add("sqlTimeout" , sqlTimeout);
        String mybatisSourceBeanName = parserContext.getReaderContext().registerWithGeneratedName(mybatisSourceBeanDefinition);

        RootBeanDefinition mybatisInterceptorBeanDefinition = new RootBeanDefinition(SqlLoggerInterceptor.class);
        mybatisInterceptorBeanDefinition.getPropertyValues().add("mybatisOperationSource" , new RuntimeBeanReference(mybatisSourceBeanName));
        parserContext.getReaderContext().registerWithGeneratedName(mybatisInterceptorBeanDefinition);

        //注册路由切换的aop
        parseDynamicDataSource(element, parserContext);
        return null;
    }



    private void parseDynamicDataSource(Element element, ParserContext parserContext){

        AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);

        Object eleSource = parserContext.extractSource(element);

        RootBeanDefinition interceptorDef = new RootBeanDefinition(DynamicDataSourceInterceptor.class);
        interceptorDef.setSource(eleSource);
        interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

        RootBeanDefinition advisorDef = new RootBeanDefinition(DynamicDataSourceAdvisor.class);
        advisorDef.setSource(eleSource);
        advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
        String advisorBeanName = parserContext.getReaderContext().registerWithGeneratedName(advisorDef);

        CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName() , eleSource);
        compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
        compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, advisorBeanName));
        parserContext.registerComponent(compositeDef);
    }
}
