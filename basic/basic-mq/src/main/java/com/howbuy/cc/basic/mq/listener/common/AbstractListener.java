package com.howbuy.cc.basic.mq.listener.common;

import com.howbuy.cc.basic.logger.CCLogger;
import org.apache.commons.lang3.StringEscapeUtils;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.Serializable;

/**
 * 公共监听的父类，子类继承即可
 * Title: AbstractListener.java
 * @author cheng.xinwei
 */
public abstract class AbstractListener implements MessageListener {  
	
	CCLogger logger = CCLogger.getLogger(this.getClass());
	
	/**
	 * 有消息时被触发
	 * @author cheng.xinwei
	 */
    @Override
    public void onMessage(Message message) {  
    	
        try {

        	if(message instanceof TextMessage){
        		String messageStr = ((TextMessage)message).getText();
        		logger.info(messageStr);
        		messageStr = StringEscapeUtils.unescapeHtml4(messageStr);
        		onMessage(messageStr);
        	}else if(message instanceof ObjectMessage){
                Serializable messageObj = ((ObjectMessage)message).getObject();
                logger.info(Json.toJson(messageObj, JsonFormat.compact()));
                onMessage(messageObj);
        	}
        }  
        catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
        }  
    }  
    
    /**
	 * 有消息时被触发，子类使用 必须实现
	 * @author cheng.xinwei
	 */
    protected void onMessage(String message){}

    protected void onMessage(Serializable message) {}

}
