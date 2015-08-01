package com.howbuy.cc.basic.mq.listener.common;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * 虚拟的tioic
 * Title: VirtualMessageListenerContainer.java
 * @author cheng.xinwei
 */
public class VirtualMessageListenerContainer extends DefaultMessageListenerContainer{

	private String destinationName;
	
	private boolean exclusive = false;
	
	private String sysName;
	
	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
		initDestination();
	}

	public void setDestinationName(String destinationName) {
		this.destinationName =  destinationName;
		initDestination();
	}
	
	public void initDestination(){
		if(this.destinationName!=null && this.sysName!=null){
			String destinationName = "Consumer."+ this.sysName + ".VirtualTopic." + this.destinationName;
			if(exclusive){
				destinationName = destinationName + "?consumer.exclusive=true";
			}
			ActiveMQQueue queue  =  new  ActiveMQQueue(destinationName);   
			super.setDestination(queue);
		}
	}
	
	
}
