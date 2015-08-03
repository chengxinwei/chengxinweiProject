package com.howbuy.activemq.sender;

import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.sender.common.QueueAbstractSender;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
@ActivemqSender(value = "test.queue")
public class TestSender extends QueueAbstractSender {

    @Override
    protected void log(String destinationName, String message) {

    }
}
