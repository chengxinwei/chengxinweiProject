package com.howbuy.cc.basic.mq.listener.common.container;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@SuppressWarnings("unused")
public class TopicMessageListenerContainer extends DefaultMessageListenerContainer{

	private String destinationName;
	
	public TopicMessageListenerContainer() throws JMSException{
		super.setPubSubDomain(true);
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
		ActiveMQTopic activeMQTopic = new ActiveMQTopic(destinationName);
		super.setDestination(activeMQTopic);
	}
	
	
	
}
