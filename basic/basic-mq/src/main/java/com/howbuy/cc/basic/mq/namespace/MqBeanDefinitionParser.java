package com.howbuy.cc.basic.mq.namespace;

import com.howbuy.cc.basic.mq.connection.ActivemqConnectionFactory;
import com.howbuy.cc.basic.mq.constant.MQConstant;
import org.apache.activemq.pool.PooledConnectionFactory;
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
        String afterTransaction = element.getAttribute("afterTransactionSend");
        String brokerUrl = element.getAttribute("brokerUrl");
        String basePackage = element.getAttribute("basePackage");
        String maxConnections = element.getAttribute("maxConnections");
        String maximumActiveSessionPerConnection = element.getAttribute("maximumActiveSessionPerConnection");
        String idleTimeout = element.getAttribute("idleTimeout");
        String expiryTimeout = element.getAttribute("expiryTimeout");
        String senderLog = element.getAttribute("senderLog");
        String listenerLog = element.getAttribute("listenerLog");
        String redeliveryListener = element.getAttribute("redeliveryListener");
        String initialRedeliveryDelay = element.getAttribute("initialRedeliveryDelay");
        String maximumRedeliveries = element.getAttribute("maximumRedeliveries");

        BeanDefinition sourceBeanDefinition = new RootBeanDefinition(MqOperationSource.class);
        sourceBeanDefinition.getPropertyValues().add("afterTransactionSend" , afterTransaction);
        sourceBeanDefinition.getPropertyValues().add("basePackage" , basePackage);
        sourceBeanDefinition.getPropertyValues().add("senderLog" , senderLog);
        sourceBeanDefinition.getPropertyValues().add("listenerLog" , listenerLog);
        sourceBeanDefinition.getPropertyValues().add("redeliveryListener" , redeliveryListener);
        sourceBeanDefinition.getPropertyValues().add("initialRedeliveryDelay" , initialRedeliveryDelay);
        sourceBeanDefinition.getPropertyValues().add("maximumRedeliveries" , maximumRedeliveries);
        parserContext.getRegistry().registerBeanDefinition(MQConstant.MQ_OPERATION_SOURCE_BEANNAME , sourceBeanDefinition);


        //ActiveMQConnectionFactory
        BeanDefinition connectionBeanDefinition = new RootBeanDefinition(ActivemqConnectionFactory.class);
        connectionBeanDefinition.getPropertyValues().add("brokerURL" , brokerUrl);
        connectionBeanDefinition.getPropertyValues().add("mqOperationSource" , new RuntimeBeanReference(MQConstant.MQ_OPERATION_SOURCE_BEANNAME));
        String connectionBeanName = parserContext.getReaderContext().registerWithGeneratedName(connectionBeanDefinition);

        //ActiveMQConnectionPoolFactory
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        BeanDefinition connectionPoolBeanDefinition = new RootBeanDefinition(PooledConnectionFactory.class);
        connectionPoolBeanDefinition.getPropertyValues().add("maxConnections" , maxConnections);
        connectionPoolBeanDefinition.getPropertyValues().add("maximumActiveSessionPerConnection" , maximumActiveSessionPerConnection);
        connectionPoolBeanDefinition.getPropertyValues().add("idleTimeout" , idleTimeout);
        connectionPoolBeanDefinition.getPropertyValues().add("expiryTimeout" , expiryTimeout);
        connectionPoolBeanDefinition.getPropertyValues().add("connectionFactory" , new RuntimeBeanReference(connectionBeanName));
        parserContext.getRegistry().registerBeanDefinition(MQConstant.MQ_CONNECTION_POOL_FACTORY_BEANNAME , connectionPoolBeanDefinition);

        return null;
    }

}
