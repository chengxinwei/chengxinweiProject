package com.howbuy.activemq.consumer;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.TopicAbstractListener;
import com.howbuy.cc.basic.mq.listener.common.VirtualAbstractListener;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
@ActivemqListener(value = "test.virtual" , systemName = "test")
public class VirtualConsumerTest extends VirtualAbstractListener {

    @Override
    public void onMessage(String message)  {
        System.out.println(message);
    }

    @Override
    public void onMessage(Serializable message)  {
        System.out.println(message);
    }
}
