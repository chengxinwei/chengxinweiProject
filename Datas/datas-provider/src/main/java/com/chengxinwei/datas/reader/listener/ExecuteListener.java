package com.chengxinwei.datas.reader.listener;

import com.chengxinwei.datas.model.Datas;
import com.chengxinwei.datas.reader.data.DbReader;
import com.chengxinwei.datas.reader.handler.DataReaderHandler;
import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.TopicAbstractListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
@ActivemqListener("datas.execute")
public class ExecuteListener extends TopicAbstractListener{

    @Autowired
    private DataReaderHandler dataReaderHandler;

    @Override
    public void onMessage(Serializable message) {
        Datas datas = (Datas)message;
        dataReaderHandler.readData(datas);
    }
}
