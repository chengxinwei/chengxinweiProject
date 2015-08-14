package com.chengxinwei.datas.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/10.
 */
@SuppressWarnings("unused")
public class Datas implements Serializable{

    private String tableName;
    private DbType fromDbType;
    private DbType toDbType;
    private List<Map<String,Object>> dataList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DbType getFromDbType() {
        return fromDbType;
    }

    public void setFromDbType(DbType fromDbType) {
        this.fromDbType = fromDbType;
    }

    public DbType getToDbType() {
        return toDbType;
    }

    public void setToDbType(DbType toDbType) {
        this.toDbType = toDbType;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }
}
