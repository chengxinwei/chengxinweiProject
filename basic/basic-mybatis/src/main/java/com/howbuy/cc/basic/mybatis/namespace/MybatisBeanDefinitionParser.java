package com.howbuy.cc.basic.mybatis.namespace;

import com.howbuy.cc.basic.config.PropertyPlaceHolderResolver;
import com.howbuy.cc.basic.mybatis.interceptor.SqlLoggerInterceptor;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

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

        return null;
    }

}
