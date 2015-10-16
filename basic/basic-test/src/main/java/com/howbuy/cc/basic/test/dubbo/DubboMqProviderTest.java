package com.howbuy.cc.basic.test.dubbo;

import com.howbuy.cc.basic.test.interfac.DubboMQInterfaceTest;
import com.howbuy.cc.basic.test.mq.QueueSender;
import com.howbuy.cc.basic.test.mq.TopicSender;
import com.howbuy.cc.basic.test.mq.VirtualSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/10/10.
 */

public class DubboMqProviderTest implements DubboMQInterfaceTest{

    @Autowired
    private QueueSender queueSender;
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private VirtualSender virtualSender;

    @Override
    public String sendQueue(String text) {
        queueSender.sendMessage(text);
        return "ok";
    }

    @Override
    public String sendTopic(String text) {
        topicSender.sendMessage(text);
        return "ok";
    }

    @Override
    public String sendVirtual(String text) {
        virtualSender.sendMessage(text);
        return "ok";
    }
}
