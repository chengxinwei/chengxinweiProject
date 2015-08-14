package com.chengxinwei.datas.reader.data;

import com.chengxinwei.datas.reader.data.common.DataReader;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
public class MongoReader  extends DataReader {

    @Override
    public Page<Map> getData(String tableName, int count, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public int count(String tableName) {
        return 0;
    }
}
