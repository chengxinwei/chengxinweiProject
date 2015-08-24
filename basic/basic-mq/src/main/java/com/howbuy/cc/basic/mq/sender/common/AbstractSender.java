package com.howbuy.cc.basic.mq.sender.common;


import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.mq.namespace.MqOperationSource;
import com.howbuy.cc.basic.mq.transaction.ActiveMQThreadLocal;
import com.howbuy.cc.basic.mq.transaction.MqTransactionSynchronization;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/**
 * 发送消息的超类
 * Title: AbstractSender.java
 * @author cheng.xinwei
 */
@SuppressWarnings("unused")
public abstract class AbstractSender {

	//发送的主题
	protected  String destinationName;
	//是否采用发布订阅模式  默认使用
	private boolean isSub = true;
    private MqOperationSource mqOperationSource;
    private JmsTemplate jmsTemplate;

	private CCLogger logger = CCLogger.getLogger(this.getClass());


    /**
     * 发送文字消息
     */
    public void sendMessage(Serializable message){
        this.doSendMessage(message, false);
    }

	/**
	 * 发送文字消息
	 */
	public void sendMessage(String message){
        this.doSendMessage(message , true);
	}


    private void doSendMessage(final Object message , final boolean isText){

        if(saveMessageIfNecessary(message)){
            return;
        }

        logger.info(isText ? message.toString() : Json.toJson(message, JsonFormat.compact()));

        jmsTemplate.send(this.getDestination() , new MessageCreator(){
            @Override
            public Message createMessage(Session session) throws JMSException {
                return getMessage(session , isText , message);
            }
        });
    }


    /**
     * 获取消息对象
     */
    private Message getMessage(Session session , boolean isText , Object message) throws JMSException {
        if(isText) {
           return session.createTextMessage(message.toString());
        }else{
            return session.createObjectMessage((Serializable) message);
        }
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
    private boolean saveMessageIfNecessary(Object message){
        //判断是否开启了事物 如果开启了事物,则不发送 放到缓冲队列中,等待 aop 结束commit
        if(mqOperationSource.isAfterTransaction()
            && TransactionSynchronizationManager.isActualTransactionActive()
            && !ActiveMQThreadLocal.isEndTransaction()){
                TransactionSynchronizationManager.registerSynchronization(new MqTransactionSynchronization(Thread.currentThread().getId()));
                ActiveMQThreadLocal.putMessage(this , message);
                return true;
        }
        return false;
    }

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
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
