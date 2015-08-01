package com.howbuy.cc.basic.mq.listener.common;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * 公共监听的父类，子类继承即可
 * Title: AbstractListener.java
 * @author cheng.xinwei
 */
public abstract class AbstractListener implements MessageListener {  
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 有消息时被触发
	 * @author cheng.xinwei
	 */
    @Override
    public void onMessage(Message message) {  
    	
        try {
        	
        	if(message instanceof TextMessage){
        		
        		String messageStr = ((TextMessage)message).getText();
        		if(logger.isDebugEnabled()){
        			logger.debug(messageStr);
        		}
        		messageStr = StringEscapeUtils.unescapeHtml4(messageStr);
        		log(messageStr);
        		onMessage(messageStr);
        	}else if(message instanceof ObjectMessage){
                logger.error("不能解析ObjectMessage" + message.toString());
        	}
        }  
        catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
        }  
    }  
    
    /**
	 * 有消息时被触发,打印日志
	 * @author cheng.xinwei
	 */
    public void log(String message){}
    /**
	 * 有消息时被触发，子类使用 必须实现
	 * @author cheng.xinwei
	 */
    public abstract void onMessage(String message) throws JMSException;
}
