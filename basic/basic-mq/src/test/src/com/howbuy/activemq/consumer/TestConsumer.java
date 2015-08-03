package com.howbuy.activemq.consumer;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.QueueAbstractListener;

import javax.jms.JMSException;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
@ActivemqListener(value = "test.queue")
public class TestConsumer extends QueueAbstractListener {

    @Override
    public void onMessage(String message) throws JMSException {
        System.out.println(message);
    }

}
