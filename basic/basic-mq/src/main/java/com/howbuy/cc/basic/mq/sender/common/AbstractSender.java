package com.howbuy.cc.basic.mq.sender.common;


import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.namespace.MqOperationSource;
import com.howbuy.cc.basic.mq.transaction.ActiveMQThreadLocal;
import com.howbuy.cc.basic.mq.transaction.MqTransactionSynchronization;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.jms.*;
import java.io.Serializable;
import java.util.Map;

/**
 * 发送消息的超类
 * Title: AbstractSender.java
 * @author cheng.xinwei
 */
@SuppressWarnings("unused")
public abstract class AbstractSender implements  BeanFactoryPostProcessor {

	//发送的主题
	protected  String destinationName;
	//是否采用发布订阅模式  默认使用
	private boolean isSub = true;
    private MqOperationSource mqOperationSource;
    private JmsTemplate jmsTemplate;

	private CCLogger logger = CCLogger.getLogger(AbstractSender.class);


    /**
     * 发送文字消息
     */
    public String sendMessage(Serializable message){
        return this.doSendMessage(message , null);
    }

    /**
     * 发送文字消息
     */
    public String sendMessage(Serializable message , Map<String,String> selector){
        return this.doSendMessage(message , selector);
    }

    private String doSendMessage(final Serializable message , final Map<String,String> selector){
        if(saveMessageIfNecessary(message)){
            return null;
        }

        final Message[] messageAry = new Message[1];
        jmsTemplate.send(this.getDestination(), new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                messageAry[0] = getMessage(session, message, selector);
                return messageAry[0];
            }
        });
        Message messageObj = messageAry[0];
        try {
            String id = messageAry[0].getJMSMessageID().toString();
            log(id , message);
            return id;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void log(String id , Serializable message){
        if(StringUtils.isEmpty(mqOperationSource.getSenderLog())){
            return;
        }
        String[] messageInfoAry = new String[2];

        messageInfoAry[0] = id;
        String text=  "";
        if(this.getClass().getAnnotation(ActivemqSender.class).logDetail()) {
           text = message instanceof String ? message.toString() : Json.toJson(message, JsonFormat.compact());
        }
        messageInfoAry[1] = text;
        logger.info("activeMQ-sender" , messageInfoAry);


    }


    /**
     * 获取消息对象
     */
    private Message getMessage(Session session ,  Serializable message , final Map<String,String> selector) throws JMSException {
        Message messageObj;
        if(message instanceof String) {
            messageObj = session.createTextMessage(message.toString());
        }else{
            messageObj = session.createObjectMessage(message);
        }
        messageObj.setJMSRedelivered(true);
        messageObj.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
        if(selector != null && !selector.isEmpty()){
            for(Map.Entry<String,String> entry : selector.entrySet()) {
                messageObj.setStringProperty(entry.getKey() , entry.getValue());
            }
        }
        return messageObj;
    }


    /**
     * 获取队列名字
     */
    private Destination getDestination(){
        if (isSub) {
            return new ActiveMQTopic(destinationName);
        } else {
            return new ActiveMQQueue(destinationName);
        }
    }

    /**
     * 判断是否开启了事务 如果开启了事务则不需要发送 等待事务完结
     */
    private boolean saveMessageIfNecessary(Serializable message){
        //判断是否开启了事物 如果开启了事物,则不发送 放到缓冲队列中,等待 aop 结束commit
        if(mqOperationSource.isAfterTransactionSend()
            && TransactionSynchronizationManager.isActualTransactionActive()
            && !ActiveMQThreadLocal.isEndTransaction()){
                TransactionSynchronizationManager.registerSynchronization(new MqTransactionSynchronization(Thread.currentThread().getId()));
                ActiveMQThreadLocal.putMessage(this , message);
                return true;
        }
        return false;
    }

    @Override
    public void postProcessBeanFactory( ConfigurableListableBeanFactory beanFactory){
        this.destinationName = this.getClass().getAnnotation(ActivemqSender.class).value();
        if(VirtualAbstractSender.class.isAssignableFrom(this.getClass())){
            this.destinationName = "VirtualTopic." + destinationName;
        }
        this.mqOperationSource = beanFactory.getBean(MqOperationSource.class);
        this.jmsTemplate = beanFactory.getBean(JmsTemplate.class);
        if(StringUtils.isEmpty(destinationName)){
            throw new RuntimeException(this.getClass().getSimpleName() + " destinationName is null");
        }
        if(StringUtils.isNotEmpty(mqOperationSource.getSenderLog())) {
            CCLoggerUtil.clearAndAddFileLog(logger, mqOperationSource.getSenderLog());
        }
    }

	public void setSub(boolean isSub) {
		this.isSub = isSub;
	}

    public MqOperationSource getMqOperationSource() {
        return mqOperationSource;
    }

    public void setMqOperationSource(MqOperationSource mqOperationSource) {
        this.mqOperationSource = mqOperationSource;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
