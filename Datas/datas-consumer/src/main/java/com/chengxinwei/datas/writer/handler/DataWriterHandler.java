package com.chengxinwei.datas.writer.handler;

import com.chengxinwei.datas.model.Datas;
import com.chengxinwei.datas.model.DbType;
import com.chengxinwei.datas.writer.writer.DbWriter;
import com.chengxinwei.datas.writer.writer.MongoWriter;
import com.howbuy.cc.basic.logger.CCLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
public class DataWriterHandler {

    @Autowired
    private MongoWriter mongoWriter;
    @Autowired
    private DbWriter dbWriter;

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());

    public void writeData(Datas datas){
        DbType dbType = datas.getToDbType();
        if(dbType.equals(DbType.mongodb)){
            mongoWriter.write(datas.getTableName() , datas.getDataList());
        }else if(dbType.equals(DbType.mysql)){
            dbWriter.write(datas.getTableName() , datas.getDataList());
        }

        ccLogger.info("数据写入完成【表名】" + datas.getTableName() + ",【条数】" +  datas.getDataList().size() + "写入完成。");
    }

}
