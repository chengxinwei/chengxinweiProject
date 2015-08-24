package com.howbuy.cc.basic.mongo.namespace;

/**
 * Created by xinwei.cheng on 2015/8/19.
 */
public class MongoOperationSource {

    private String mongoTimeLog;
    private int mongoTimeout;

    public String getMongoTimeLog() {
        return mongoTimeLog;
    }

    public void setMongoTimeLog(String mongoTimeLog) {
        this.mongoTimeLog = mongoTimeLog;
    }

    public int getMongoTimeout() {
        return mongoTimeout;
    }

    public void setMongoTimeout(int mongoTimeout) {
        this.mongoTimeout = mongoTimeout;
    }
}
