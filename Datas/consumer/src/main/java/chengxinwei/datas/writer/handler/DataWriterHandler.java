package chengxinwei.datas.writer.handler;

import chengxinwei.datas.writer.writer.common.DataWriter;
import com.howbuy.cc.basic.mybatis.model.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
public class DataWriterHandler {

    private DataWriter dataWriter;

    public DataWriterHandler(DataWriter dataWriter){
        this.dataWriter = dataWriter;
    }

    public void writeData(List<Map> list){
        dataWriter.write(list);
    }

}
