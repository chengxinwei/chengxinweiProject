package com.howbuy.cc.basic.mq.listener.common.container;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@SuppressWarnings("unused")
public class TopicMessageListenerContainer extends AbstractMessageListenerContainer{

    @Override
    public Destination getDestination(String destinationName) {
        return new ActiveMQTopic(destinationName);
    }

}
