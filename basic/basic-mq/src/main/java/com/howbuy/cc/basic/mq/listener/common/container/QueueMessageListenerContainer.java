package com.howbuy.cc.basic.mq.listener.common.container;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;

public class QueueMessageListenerContainer extends AbstractMessageListenerContainer {


    @Override
    public Destination getDestination(String destinationName) {
        return new  ActiveMQQueue(destinationName);
    }


}
