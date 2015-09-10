package com.howbuy.activemq.sender;

import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.sender.common.QueueAbstractSender;
import com.howbuy.cc.basic.mq.sender.common.VirtualAbstractSender;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/8/25.
 */
@Service
@ActivemqSender(value = "test.virtual.exception")
public class ExceptionVirtualSenderTest extends VirtualAbstractSender {
}
