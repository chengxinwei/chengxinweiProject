package com.chengxinwei.datas.writer.writer;

import com.chengxinwei.datas.writer.dao.WriterDao;
import com.chengxinwei.datas.writer.writer.common.DataWriter;
import com.howbuy.cc.basic.logger.CCLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
public class DbWriter extends DataWriter {


    @Autowired
    private WriterDao writerDao;

    @Override
    public void write(String tableName , List<Map<String,Object>> list) {
        for(Map<String,Object> map : list){
            writerDao.insert(tableName , map);
        }
    }
}
