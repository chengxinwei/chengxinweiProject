package com.howbuy.cc.basic.mq.listener.common.container;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;

/**
 * 虚拟的tioic
 * Title: VirtualMessageListenerContainer.java
 * @author cheng.xinwei
 */
public class VirtualMessageListenerContainer extends AbstractMessageListenerContainer{

	private String sysName;

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}


    @Override
    public Destination getDestination(String destinationName) {
        if(StringUtils.isEmpty(sysName)){
            throw new IllegalArgumentException("VirtualAbstractListener 必须设置 systemName");
        }
        destinationName = "Consumer."+ this.sysName + ".VirtualTopic." + destinationName;
        return new  ActiveMQQueue(destinationName);
    }

}
