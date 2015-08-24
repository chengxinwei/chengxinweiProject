package com.howbuy.cc.basic.mq.namespace;

import com.howbuy.cc.basic.mq.common.ActiveMqInit;
import com.howbuy.cc.basic.mq.common.BeanScanner;
import com.howbuy.cc.basic.mq.constant.MQConstant;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.jms.core.JmsTemplate;
import org.w3c.dom.Element;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
@SuppressWarnings("unused")
public class MqBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        boolean afterTransaction = Boolean.parseBoolean(element.getAttribute("afterTransaction"));
        String brokerUrl = element.getAttribute("brokerUrl");
        String basePackage = element.getAttribute("basePackage");

        BeanDefinition sourceBeanDefinition = new RootBeanDefinition(MqOperationSource.class);
        sourceBeanDefinition.getPropertyValues().add("afterTransaction" , afterTransaction);
        sourceBeanDefinition.getPropertyValues().add("brokerUrl" , brokerUrl);
        sourceBeanDefinition.getPropertyValues().add("basePackage" , basePackage);
        parserContext.getRegistry().registerBeanDefinition(MQConstant.MQ_OPERATION_SOURCE_BEANNAME , sourceBeanDefinition);

        //ActiveMQConnectionFactory
        BeanDefinition connectionBeanDefinition = new RootBeanDefinition(ActiveMQConnectionFactory.class);
        connectionBeanDefinition.getPropertyValues().add("brokerURL" , brokerUrl);
        parserContext.getRegistry().registerBeanDefinition(MQConstant.MQ_CONNECTION_FACTORY_BEANNAME, connectionBeanDefinition);

        //jmsTemplate
        BeanDefinition jmsTemplateBeanDefinition = new RootBeanDefinition(JmsTemplate.class);
        jmsTemplateBeanDefinition.getPropertyValues().add("connectionFactory" , new RuntimeBeanReference(MQConstant.MQ_CONNECTION_FACTORY_BEANNAME));
        parserContext.getRegistry().registerBeanDefinition(MQConstant.MQ_JMSTEMPLATE_BEANNAME, jmsTemplateBeanDefinition);


        RootBeanDefinition initBeanDefinition = new RootBeanDefinition(ActiveMqInit.class);
        initBeanDefinition.getPropertyValues().add("mqOperationSource" , new RuntimeBeanReference(MQConstant.MQ_OPERATION_SOURCE_BEANNAME));
        initBeanDefinition.getPropertyValues().add("activeMQConnectionFactory" , new RuntimeBeanReference(MQConstant.MQ_CONNECTION_FACTORY_BEANNAME));
        parserContext.getReaderContext().registerWithGeneratedName(initBeanDefinition);

        RootBeanDefinition scanDefinition = new RootBeanDefinition(BeanScanner.class);
        scanDefinition.getPropertyValues().add("mqOperationSource" , new RuntimeBeanReference(MQConstant.MQ_OPERATION_SOURCE_BEANNAME));
        parserContext.getReaderContext().registerWithGeneratedName(scanDefinition);

        return null;
    }

}
