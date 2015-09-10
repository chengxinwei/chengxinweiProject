package com.howbuy.activemq.consumer;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.QueueAbstractListener;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/9/6.
 */
@Service
@ActivemqListener(value = "selector.queue" , selector = "age='1'")
public class SelectQueueConsumer extends QueueAbstractListener{
    @Override
    public void onMessage(String id, Serializable message) {
        System.out.println(message);
    }
}
