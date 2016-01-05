package com.howbuy.activemq.sender;

import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.sender.common.QueueAbstractSender;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/8/25.
 */
@Service
@ActivemqSender("test.queue.exception")
public class ExceptionQueueSenderTest extends QueueAbstractSender {
}
