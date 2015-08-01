package com.howbuy.cc.basic.mq.sender.common;


import com.howbuy.cc.basic.mq.common.ActiveMQThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

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
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected abstract void log(String destinationName, String message);

	/**
	 * 发送文字消息
	 * @author cheng.xinwei
	 * @param message 消息
	 * @throws Exception 
	 */
	public void sendMessage(String message) throws Exception {
		//判断是否开启了事物 如果开启了事物,则不发送 放到缓冲队列中,等待 aop 结束commit
		if(ActiveMQThreadLocal.isOpenActiveMQTransactional()){
			ActiveMQThreadLocal.putMessage(this , message);
			return;
		}
		if(logger.isDebugEnabled()){
			logger.debug(message);
		}
		log(destinationName, message);
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
			if (isSub) {
				destination = session.createTopic(destinationName);
			} else {
				// 设置Queue 名字
				destination = session.createQueue(destinationName);
			}
			// 创建生产消息对象
			producer = session.createProducer(destination);
//			producer.setTimeToLive(timetolive);
			TextMessage textMessage = session.createTextMessage(message);
			producer.send(textMessage);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable e1) {
			    throw e1;
			}
		}
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
