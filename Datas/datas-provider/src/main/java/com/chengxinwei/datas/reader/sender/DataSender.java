package com.chengxinwei.datas.reader.sender;

import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.sender.common.QueueAbstractSender;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
@ActivemqSender("datas.queue")
public class DataSender extends QueueAbstractSender{


}
