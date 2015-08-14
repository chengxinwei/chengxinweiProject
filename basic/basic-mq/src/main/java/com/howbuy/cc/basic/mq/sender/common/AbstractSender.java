package com.howbuy.cc.basic.mq.sender.common;


import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.mq.common.ActiveMQThreadLocal;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 发送消息的超类
 * Title: AbstractSender.java
 * @author cheng.xinwei
 */
public abstract class AbstractSender {

	//链接工厂
	private ConnectionFactory connectionFactory;
	//发送的主题
	protected  String destinationName;
	//是否采用发布订阅模式  默认使用
	private boolean isSub = true;
	
	CCLogger logger = CCLogger.getLogger(this.getClass());


    /**
     * 发送文字消息
     * @author cheng.xinwei
     * @param message 消息
     * @throws Exception
     */
    public void sendMessage(Serializable message){
        this.doSendMessage(message, false);
    }

	/**
	 * 发送文字消息
	 * @author cheng.xinwei
	 * @param message 消息
	 * @throws Exception 
	 */
	public void sendMessage(String message){
        this.doSendMessage(message , true);
	}


    private void doSendMessage(Object message , boolean isText){
        if(saveMessageIfNecessary(message)){
            return;
        }
        logger.info(isText ? message.toString() : Json.toJson(message, JsonFormat.compact()));

        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            Destination destination = getDestination(session);
            // 创建生产消息对象
            MessageProducer producer = session.createProducer(destination);
            Message messageObj = this.getMessage(session , isText , message);
            producer.send(messageObj);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    //ignore
                }
            }
            if(session !=null){
                try {
                    session.close();
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }


    /**
     * 获取消息对象
     * @param session
     * @param isText
     * @param message
     * @return
     * @throws JMSException
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
     * @return
     */
    private Destination getDestination(Session session) throws JMSException {
        if (isSub) {
            return session.createTopic(destinationName);
        } else {
            return session.createQueue(destinationName);
        }
    }


    /**
     * 判断是否开启了事务 如果开启了事务则不需要发送 等待事务完结
     * @param message
     * @return
     */
    private boolean saveMessageIfNecessary(Object message){
        //判断是否开启了事物 如果开启了事物,则不发送 放到缓冲队列中,等待 aop 结束commit
        if(ActiveMQThreadLocal.isOpenActiveMQTransactional()){
            ActiveMQThreadLocal.putMessage(this , message);
            return true;
        }
        return false;
    }


	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public boolean isSub() {
		return isSub;
	}

	public void setSub(boolean isSub) {
		this.isSub = isSub;
	}


}
