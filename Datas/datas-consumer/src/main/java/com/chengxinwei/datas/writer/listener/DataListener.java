package com.chengxinwei.datas.writer.listener;

import com.chengxinwei.datas.model.Datas;
import com.chengxinwei.datas.writer.handler.DataWriterHandler;
import com.chengxinwei.datas.writer.writer.DbWriter;
import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.QueueAbstractListener;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
@ActivemqListener("datas.queue")
public class DataListener extends QueueAbstractListener {

    @Autowired
    private DataWriterHandler dataWriterHandler;

    @Override
    public void onMessage(Serializable serializable) {
        Datas datas = (Datas)serializable;
        dataWriterHandler.writeData(datas);
    }
}
