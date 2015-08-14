package com.chengxinwei.datas.writer.writer.common;

import com.howbuy.cc.basic.mybatis.model.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
public abstract class DataWriter {

    public abstract void write(String tableName , List<Map<String,Object>> list);

}
