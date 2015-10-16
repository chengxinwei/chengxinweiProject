package com.howbuy.cc.basic.test.mq;

import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.sender.common.QueueAbstractSender;
import com.howbuy.cc.basic.mq.sender.common.VirtualAbstractSender;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/10/10.
 */
@Service
@ActivemqSender("user.queue")
public class VirtualSender extends VirtualAbstractSender {

}
