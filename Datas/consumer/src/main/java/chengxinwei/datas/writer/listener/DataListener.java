package chengxinwei.datas.writer.listener;

import chengxinwei.datas.writer.handler.DataWriterHandler;
import chengxinwei.datas.writer.writer.DbWriter;
import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.listener.common.QueueAbstractListener;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.xml.ws.ServiceMode;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
@ActivemqListener("datas.queue")
public class DataListener extends QueueAbstractListener {

    @Autowired
    private DbWriter dbWriter;

    @Override
    public void onMessage(String message) throws JMSException {
        DataWriterHandler dataWriterHandler = new DataWriterHandler(dbWriter);
        List<Map> list = Json.fromJson(List.class, message);
        dataWriterHandler.writeData(list);
    }
}
