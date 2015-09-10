package com.howbuy.cc.basic.mq.listener.common.container;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;

public abstract class AbstractMessageListenerContainer extends DefaultMessageListenerContainer {

	private String destinationName;
    private Integer prefetchSize;
    private String selector;
	
	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

    public Integer getPrefetchSize() {
        return prefetchSize;
    }

    public void setPrefetchSize(Integer prefetchSize) {
        this.prefetchSize = prefetchSize;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    @Override
    public void afterPropertiesSet() {
        destinationName = destinationName + "?consumer.prefetchSize=" + prefetchSize;
        Destination destination  =  getDestination(destinationName);
        super.setDestination(destination);
        if(StringUtils.isNotEmpty(selector)) {
            super.setMessageSelector(this.selector);
        }
        super.afterPropertiesSet();
    }

    public abstract Destination getDestination(String destinationName);

}
