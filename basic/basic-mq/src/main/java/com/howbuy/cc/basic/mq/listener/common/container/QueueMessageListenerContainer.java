package com.howbuy.cc.basic.mq.listener.common.container;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class QueueMessageListenerContainer extends DefaultMessageListenerContainer{

	private String destinationName;
	
	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
		ActiveMQQueue queue  =  new  ActiveMQQueue(destinationName);
		super.setDestination(queue);
	}
	
	
}
