package com.howbuy.cc.basic.mq.listener.common;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class QueueMessageListenerContainer extends DefaultMessageListenerContainer{

	private String destinationName;
	
	private boolean exclusive = false;
	
	private int priority = 0;
	
	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
		if(exclusive){
			destinationName = destinationName + "?consumer.exclusive=true";
			if(priority>0){
				destinationName = destinationName + "&consumer.priority="+priority;
			}
		}
		ActiveMQQueue queue  =  new  ActiveMQQueue(destinationName);   
		super.setDestination(queue);
	}
	
	
}
