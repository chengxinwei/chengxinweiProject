package com.howbuy.cc.basic.mybatis.namespace;

/**
 * Created by xinwei.cheng on 2015/8/19.
 */
public class MybatisOperationSource {

    private String sqlTimeLog;
    private Integer sqlTimeout;

    public String getSqlTimeLog() {
        return sqlTimeLog;
    }

    public void setSqlTimeLog(String sqlTimeLog) {
        this.sqlTimeLog = sqlTimeLog;
    }

    public Integer getSqlTimeout() {
        return sqlTimeout;
    }

    public void setSqlTimeout(Integer sqlTimeout) {
        this.sqlTimeout = sqlTimeout;
    }
}
