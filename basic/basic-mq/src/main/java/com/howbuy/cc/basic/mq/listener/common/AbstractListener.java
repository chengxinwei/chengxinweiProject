package com.howbuy.cc.basic.mq.listener.common;

import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.constant.MQConstant;
import com.howbuy.cc.basic.mq.listener.common.container.QueueMessageListenerContainer;
import com.howbuy.cc.basic.mq.listener.common.container.TopicMessageListenerContainer;
import com.howbuy.cc.basic.mq.listener.common.container.VirtualMessageListenerContainer;
import com.howbuy.cc.basic.mq.namespace.MqOperationSource;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共监听的父类，子类继承即可
 * Title: AbstractListener.java
 * @author cheng.xinwei
 */
public abstract class AbstractListener implements MessageListener , BeanFactoryAware , BeanNameAware {
	
	private CCLogger logger = CCLogger.getLogger(AbstractListener.class);

    private String beanName;

    private MqOperationSource mqOperationSource;

	/**
	 * 有消息时被触发
	 * @author cheng.xinwei
	 */
    @Override
    public void onMessage(Message message) {  
        try {
            log(message);
            String id = message.getJMSMessageID();
            if(message instanceof TextMessage){
        		String messageStr = ((TextMessage)message).getText();
        		messageStr = StringEscapeUtils.unescapeHtml4(messageStr);
        		onMessage(id , messageStr);
        	}else if(message instanceof ObjectMessage){
                Serializable messageObj = ((ObjectMessage)message).getObject();
                onMessage(id , messageObj);
        	}else{
                logger.error("message is not TextMessage or ObjectMessage" , new IllegalArgumentException());
            }
        } catch (JMSException ex) {
            logger.error(ex.getMessage() , ex);
        }  
    }


    private void log(Message message) throws JMSException {
        if(Strings.isEmpty(mqOperationSource.getListenerLog())){
            return;
        }
        String id = message.getJMSMessageID();
        String text = "";
        if(this.getClass().getAnnotation(ActivemqListener.class).logDetail()) {
            if (message instanceof TextMessage) {
                text = StringEscapeUtils.unescapeHtml4(((TextMessage) message).getText());
            } else if (message instanceof ObjectMessage) {
                text = Json.toJson(((ObjectMessage) message).getObject(), JsonFormat.compact());
            } else {
                text = message.toString();
            }
        }
        String[] messageInfoAry = new String[3];
        messageInfoAry[0] = id;
        messageInfoAry[1] = this.getClass().getAnnotation(ActivemqListener.class).value();
        messageInfoAry[2] = text;
        logger.info("activeMQ-listener" , messageInfoAry);
    }
    
    /**
	 * 有消息时被触发，子类使用 必须实现
	 * @author cheng.xinwei
	 */
    public abstract void onMessage(String id , Serializable message);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException{
        if(!(beanFactory instanceof DefaultListableBeanFactory)){
            throw new RuntimeException("beanFactory is not DefaultListableBeanFactory class , can not regist listener");
        }
        Class<?> clazz = this.getClass();
        ActivemqListener activemqListenerAnno = clazz.getAnnotation(ActivemqListener.class);
        if(activemqListenerAnno == null) {
            return;
        }

        MqOperationSource mqOperationSource;
        try {
            mqOperationSource = beanFactory.getBean(MqOperationSource.class);
        }catch(NoSuchBeanDefinitionException e){
            logger.warn("未检测到activemq驱动,无法初始化listener");
            return;
        }
        this.mqOperationSource = mqOperationSource;

        Class<?> registClazz;

        Map<String , Object> params = new HashMap<>();

        //依据不同的消息类型做不同的处理
        if(QueueAbstractListener.class.isAssignableFrom(clazz)){
            params.put("selector" , activemqListenerAnno.selector());
            registClazz = QueueMessageListenerContainer.class;
        }else if(TopicAbstractListener.class.isAssignableFrom(clazz)){
            if(StringUtils.isNotEmpty(activemqListenerAnno.clientId())){
                params.put("clientId", activemqListenerAnno.clientId());
                params.put("subscriptionDurable" , true);
            }
            params.put("pubSubDomain" , true);
            registClazz =  TopicMessageListenerContainer.class;
        }else if(VirtualAbstractListener.class.isAssignableFrom(clazz)){
            registClazz =  VirtualMessageListenerContainer.class;
            params.put("selector" , activemqListenerAnno.selector());
            params.put("sysName", activemqListenerAnno.systemName());
        }else{
            throw new RuntimeException("abstractListener 没有继承 QueueAbstractListener | TopicAbstractListener | VirtualAbstractListener ， 无法识别是哪类监听端" );
        }

        RootBeanDefinition registBean = new RootBeanDefinition(registClazz);
        //初始化参数

        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues(params);
        mutablePropertyValues.add("sessionTransacted" , mqOperationSource.isRedeliveryListener()) ;
        mutablePropertyValues.add("prefetchSize" , activemqListenerAnno.prefetchSize());
        mutablePropertyValues.add("destinationName" , activemqListenerAnno.value());
        mutablePropertyValues.add("concurrentConsumers" , activemqListenerAnno.threadCount());
        mutablePropertyValues.add("connectionFactory" , new RuntimeBeanReference(MQConstant.MQ_CONNECTION_POOL_FACTORY_BEANNAME));
        mutablePropertyValues.add("messageListener" , new RuntimeBeanReference(beanName));
        registBean.setPropertyValues(mutablePropertyValues);
        //注册
        String containerBeanName = registClazz.getSimpleName() + "." +  this.getClass().getSimpleName();
        ((DefaultListableBeanFactory)beanFactory).registerBeanDefinition(containerBeanName, registBean);
        beanFactory.getBean(containerBeanName);


        if(StringUtils.isNotEmpty(mqOperationSource.getListenerLog())) {
            CCLoggerUtil.clearAndAddFileLog(logger, mqOperationSource.getListenerLog());
        }
        return;
    }

    @Override
    public void setBeanName(String beanName){
        this.beanName = beanName;
    }



}
