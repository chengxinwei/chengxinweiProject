package com.howbuy.activemq.consumer;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.TopicAbstractListener;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
@Service
@ActivemqListener(value = "test.topic")
public class TopicClientConsumerTest extends TopicAbstractListener {


    @Override
    public void onMessage(String id , Serializable message)  {
        System.out.println(this.getClass() + "," + id + "," + message);
    }
}
