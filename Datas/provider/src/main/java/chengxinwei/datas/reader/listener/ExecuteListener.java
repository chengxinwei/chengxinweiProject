package chengxinwei.datas.reader.listener;

import chengxinwei.datas.reader.data.DbReader;
import chengxinwei.datas.reader.handler.DataReaderHandler;
import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.TopicAbstractListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
@ActivemqListener("datas.execute")
public class ExecuteListener extends TopicAbstractListener{

    @Autowired
    private DbReader dbReader;

    @Override
    public void onMessage(String message) throws JMSException {
        DataReaderHandler dataReaderHandler = new DataReaderHandler(dbReader);
        String tableName = message;
        dataReaderHandler.readData(tableName);
    }
}
