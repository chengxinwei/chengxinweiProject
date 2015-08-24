package com.howbuy.activemq.sender;

import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.sender.common.QueueAbstractSender;
import com.howbuy.cc.basic.mq.sender.common.TopicAbstractSender;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
@ActivemqSender(value = "test.topic")
public class TopicSenderTest extends TopicAbstractSender {

}
