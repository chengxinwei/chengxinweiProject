package com.howbuy.cc.basic.mq.common;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.constant.MQConstant;
import com.howbuy.cc.basic.mq.listener.common.AbstractListener;
import com.howbuy.cc.basic.mq.listener.common.QueueAbstractListener;
import com.howbuy.cc.basic.mq.listener.common.TopicAbstractListener;
import com.howbuy.cc.basic.mq.listener.common.VirtualAbstractListener;
import com.howbuy.cc.basic.mq.listener.common.container.QueueMessageListenerContainer;
import com.howbuy.cc.basic.mq.listener.common.container.TopicMessageListenerContainer;
import com.howbuy.cc.basic.mq.listener.common.container.VirtualMessageListenerContainer;
import com.howbuy.cc.basic.mq.namespace.MqOperationSource;
import com.howbuy.cc.basic.mq.sender.common.AbstractSender;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import java.util.HashMap;
import java.util.Map;

/**
 * MQ的初始化bean
 * cheng.xinwei
 * Title: ActiveMqInit.java
 * @author cheng.xinwei
 */
public class ActiveMqInit implements BeanFactoryAware {

    private ActiveMQConnectionFactory activeMQConnectionFactory;
    private MqOperationSource mqOperationSource;
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
        initListener();
    }


    /**
     * 初始化listener
     */
    public void initListener() {

    }


    public ActiveMQConnectionFactory getActiveMQConnectionFactory() {
        return activeMQConnectionFactory;
    }

    public void setActiveMQConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        this.activeMQConnectionFactory = activeMQConnectionFactory;
    }

    public MqOperationSource getMqOperationSource() {
        return mqOperationSource;
    }

    public void setMqOperationSource(MqOperationSource mqOperationSource) {
        this.mqOperationSource = mqOperationSource;
    }



}
