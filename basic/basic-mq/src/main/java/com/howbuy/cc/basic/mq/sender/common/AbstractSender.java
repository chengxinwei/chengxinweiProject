package com.howbuy.cc.basic.mq.sender.common;


import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.namespace.MqOperationSource;
import com.howbuy.cc.basic.mq.transaction.ActiveMQThreadLocal;
import com.howbuy.cc.basic.mq.transaction.MqTransactionSynchronization;
import com.howbuy.cc.basic.spring.SpringBean;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.io.Serializable;
import java.util.Map;

/**
 * 发送消息的超类
 * Title: AbstractSender.java
 * @author cheng.xinwei
 */
@SuppressWarnings("unused")
public abstract class AbstractSender{

	//发送的主题
	protected  String destinationName;
	//是否采用发布订阅模式  默认使用
	private boolean isSub = true;
    @Autowired
    private MqOperationSource mqOperationSource;
    @Autowired
    private PooledConnectionFactory pooledConnectionFactory;

	private CCLogger logger = CCLogger.getLogger(AbstractSender.class);


    /**
     * 发送文字消息
     */
    public String sendMessage(Serializable message){
        return this.sendMessage(message, null, null);
    }

    /**
     * 发送文字消息
     */
    public String sendMessage(Serializable message , Map<String,String> selector){
        return this.sendMessage(message, null, selector);
    }

    public String sendMessage(Serializable message , Integer priority ,  final Map<String,String> selector) {
        if(saveMessageIfNecessary(message)){
            return null;
        }

        final Message[] messageAry = new Message[1];
        Connection connection = null;
        Session session = null;
        try {
            connection = pooledConnectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = this.getDestination();
            MessageProducer producer = session.createProducer(destination);//创建消息生产者
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);//指定传输模式-非持久性消息
            if(priority != null) {
                producer.setPriority(priority);
            }
            Message messageObj = this.getMessage(session , message , selector);
            producer.send(messageObj);//发送消息
            log(messageObj.getJMSMessageID() , message);
            return messageObj.getJMSMessageID() ;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }finally {
            if(session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    //ignore
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    //ignore
                }
            }
        }
    }

    public void log(String id , Serializable message){
        if(StringUtils.isEmpty(mqOperationSource.getSenderLog())){
            return;
        }
        String[] messageInfoAry = new String[3];

        messageInfoAry[0] = id;
        messageInfoAry[1] = this.destinationName;
        String text=  "";
        if(this.getClass().getAnnotation(ActivemqSender.class).logDetail()) {
           text = message instanceof String ? message.toString() : Json.toJson(message, JsonFormat.compact());
        }
        messageInfoAry[2] = text;
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

    @PostConstruct
    public void postProcessBeanFactory(){
        this.destinationName = this.getClass().getAnnotation(ActivemqSender.class).value();
        if(VirtualAbstractSender.class.isAssignableFrom(this.getClass())){
            this.destinationName = "VirtualTopic." + destinationName;
        }
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

}
