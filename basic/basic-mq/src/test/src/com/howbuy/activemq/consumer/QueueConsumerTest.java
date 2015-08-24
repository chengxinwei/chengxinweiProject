package com.howbuy.activemq.consumer;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.QueueAbstractListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
@ActivemqListener(value = "test.queue")
public class QueueConsumerTest extends QueueAbstractListener {

    @Override
    public void onMessage(String message)  {
        System.out.println(message);
    }

    @Override
    public void onMessage(Serializable message)  {
        System.out.println(message);
    }
}
